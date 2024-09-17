package Integration.jira;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;

public class CreateIssueJira {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://chattychatty.atlassian.net/";
        String createIssueResponse=given().header("Content-Type","application/json")
                .header("Authorization","Basic cmFzaGV2Y2hlbmtvb0BnbWFpbC5jb206QVRBVFQzeEZmR0YwcmIyQjhiMzYzMUozeVpJeWpzeGhxSGw0RU1ZX3VSSjZIRTkzU1VoUXFCWDlIT0R2SUQ5cDVZNEFQRFExaUdqek1LaUM0LWxtX2ZsQVVJMURzdUdhRE1LTldTSVFjbnV6VlctWDg4WGxwWkNMV2lGa0tOSExDallCTGVfUXY1LVBnZVJmMFdqU2tfcV9xeHRwdnJYdk9ENmR6MUw5aF9TaWc4YWN4aWtQMkFBPTFCRjhFMkEx")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"QES\"\n" +
                        "       },\n" +
                        "       \"summary\": \"WebSitee are not working \",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .log().all()
                .when().post("rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(createIssueResponse);
        String issueId = jsonPath.getString("id");
        System.out.println(issueId);
        //Add Attachment

        given().pathParam("key",issueId)
                .header("X-Atlassian-Token","no-check")
                .header("Authorization","Basic cmFzaGV2Y2hlbmtvb0BnbWFpbC5jb206QVRBVFQzeEZmR0YwcmIyQjhiMzYzMUozeVpJeWpzeGhxSGw0RU1ZX3VSSjZIRTkzU1VoUXFCWDlIT0R2SUQ5cDVZNEFQRFExaUdqek1LaUM0LWxtX2ZsQVVJMURzdUdhRE1LTldTSVFjbnV6VlctWDg4WGxwWkNMV2lGa0tOSExDallCTGVfUXY1LVBnZVJmMFdqU2tfcV9xeHRwdnJYdk9ENmR6MUw5aF9TaWc4YWN4aWtQMkFBPTFCRjhFMkEx")
                .multiPart("file",new File("src/test/java/Integration/Attachment/PositiveTest.png")).log().all()
                .when().post("rest/api/3/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);

    }
}
