package Integration;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AddPlaceApi {
    @Test
    public void addPlace() {
        // validate if Add Place is working as expected
        // given - all input details
        // when - submit the API
        // Then - validate the response
        // Add place -- Update Place with nw Address--Get Place to validate if new Address is present in response
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(Payload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
        ;
        JsonPath jsonPath=ReUsableMethods.rowToJson(response);// for parsing Json
        String placeId = jsonPath.getString("place_id");


        // Update Address
        String newAddress = "Ostring 12";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Address
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath1=ReUsableMethods.rowToJson(getPlaceResponse);
        String addressFromResponse = jsonPath1.getString("address");

        Assert.assertEquals(newAddress, addressFromResponse);
    }
}
