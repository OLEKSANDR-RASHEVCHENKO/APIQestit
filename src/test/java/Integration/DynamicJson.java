package Integration;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbt,String altb) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(Payload.addBook(isbt,altb))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath jsonPath = ReUsableMethods.rowToJson(response);
        String id = jsonPath.get("ID");
        System.out.println(id);
        // delete book
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        return new Object[][]{{"jsdfj","234845"},{"uzjld","90064fd"},{"uthdfk","jsdfjl22"}};
    }
}
