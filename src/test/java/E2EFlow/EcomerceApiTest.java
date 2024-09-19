package E2EFlow;

import E2EFlow.pojo.LoginReq;
import E2EFlow.pojo.LoginResponse;
import E2EFlow.pojo.Orders;
import E2EFlow.pojo.OrdersDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class EcomerceApiTest {
    public static void main(String[] args) {
        RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();
        LoginReq loginReq = new LoginReq();
        loginReq.setUserEmail("rashevchenkooleksandr@gmail.com");
        loginReq.setUserPassword("Gazmanov1234");

        RequestSpecification reqLogin=given().log().all().spec(req).body(loginReq);

               LoginResponse loginResponse= reqLogin.when().post("api/ecom/auth/login")
                .then().extract().response().as(LoginResponse.class);
        String token=loginResponse.getToken();
        String userId = loginResponse.getUserId();

        // add product
        RequestSpecification addProductReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("authorization",token).build();


       RequestSpecification requestSpecificationAddProject = given().log().all().spec(addProductReq)
                .param("productName","Laptpo")
                .param("productAddedBy",userId)
                .param("productCategory","fashion")
                .param("productSubCategory","shirts")
                .param("productPrice","1000")
                .param("productDescription","Addias Originals")
                .param("productFor","women")
                .multiPart("productImage",new File("C:\\Users\\OleksandrRashevchenk\\IdeaProjects\\API\\src\\test\\java\\E2EFlow\\images\\unnamed.jpg"));

       String addProductResponse=requestSpecificationAddProject.when().post("api/ecom/product/add-product")
               .then().log().all().extract().response().asString();
        JsonPath jsonPath = new JsonPath(addProductResponse);
        String productId=jsonPath.getString("productId");

        // create Order
        RequestSpecification createOrderReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("authorization",token).setContentType(ContentType.JSON).build();

        OrdersDetails ordersDetails = new OrdersDetails();
        ordersDetails.setCountry("Ukraine");
        List<OrdersDetails> orderDetailsList = new ArrayList();
        orderDetailsList.add(ordersDetails);
        ordersDetails.setProductOrderedId(productId);

        Orders orders = new Orders();
        orders.setOrders(orderDetailsList);

       RequestSpecification createOrdersReq= given().log().all().spec(createOrderReq).body(orders);

       String responseAddOrder=createOrdersReq.when().post("api/ecom/order/create-order").then().log().all().extract().response().asString();
        System.out.println(responseAddOrder);

        //Delete Product

        RequestSpecification deleteProductReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("authorization",token).setContentType(ContentType.JSON).build();

        RequestSpecification deleteProdReq=given().log().all().spec(deleteProductReq).pathParam("productId",productId);
        String responseDeleteProduct=deleteProdReq.delete("api/ecom/product/delete-product/{productId}").
                then().log().all().extract().response().asString();
        JsonPath jsonPath1 = new JsonPath(responseDeleteProduct);
        String message = jsonPath1.getString("message");
        Assert.assertEquals("Product Deleted Successfully",message);



    }
}
