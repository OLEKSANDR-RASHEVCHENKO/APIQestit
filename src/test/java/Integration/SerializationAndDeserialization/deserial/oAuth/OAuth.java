package Integration.SerializationAndDeserialization.deserial.oAuth;

import Integration.SerializationAndDeserialization.deserial.Api;
import Integration.SerializationAndDeserialization.deserial.GetCourse;
import Integration.SerializationAndDeserialization.deserial.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuth {
    public static void main(String[] args) {
        String[] courseTitles ={"Selenium Webdriver Java","Cypress","Protractor"};

        String response=given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                .then().extract().response().asString();
        System.out.println(response);


        JsonPath jsonPath = new JsonPath(response);
        String accessToken=jsonPath.get("access_token");

        GetCourse gt=given().queryParam("access_token",accessToken)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                        .then().extract().as(GetCourse.class);
        System.out.println(gt.getUrl());
        System.out.println(gt.getLinkedIn());
        System.out.println(gt.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = gt.getCourses().getApi();
        for (int i =0;i<apiCourses.size();i++){
           if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
               System.out.println(apiCourses.get(i).getPrice());
               System.out.println(apiCourses.get(i).getCourseTitle());
           }
        }

        //Get the course name of the WebAutomation
        List<WebAutomation> w =gt.getCourses().getWebAutomation();
        ArrayList<String> a = new ArrayList<String>();
        for (int i =0;i<w.size();i++){
            a.add(w.get(i).getCourseTitle());
        }
        List<String> expectedList=Arrays.asList(courseTitles);

        Assert.assertTrue(a.equals(expectedList));

    }
}
