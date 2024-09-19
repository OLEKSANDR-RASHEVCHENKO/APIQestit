package Integration.specBuilderTest;

import Integration.SerializationAndDeserialization.deserial.Serializ.AddPlace;
import Integration.SerializationAndDeserialization.deserial.Serializ.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Serialization {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setAddress("Ostring 52");
        addPlace.setLanguage("Deutsch");
        addPlace.setPhone_number("+94923234234");
        addPlace.setWebsite("https//lsdjfjsdfsdf");
        addPlace.setName("Alex");
        List<String> myList = new ArrayList<String>();
        myList.add("shoe park");
        myList.add("ldldld");
        addPlace.setTypes(myList);
        Location location = new Location();
        location.setLat(33.43);
        location.setLng(99.554);
        addPlace.setLocation(location);


        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification res =  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification reqq = given().spec(req)
                .body(addPlace);

        Response response = reqq.when().post("maps/api/place/add/json")
                .then().spec(res).extract().response();
        String responseINString = response.asString();
        System.out.println(responseINString);
    }
}
