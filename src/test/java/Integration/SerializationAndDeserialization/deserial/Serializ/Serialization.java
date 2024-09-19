package Integration.SerializationAndDeserialization.deserial.Serializ;

import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

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
        String response=given().log().all().queryParam("key","qaclick123")
                .body(addPlace)
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }
}
