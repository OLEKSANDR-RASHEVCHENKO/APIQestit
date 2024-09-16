package CopmpexJson;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPath {
    public static void main(String[] args) {
        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());
        //Print number of courses returned by API
        int numberOfCourse=jsonPath.getInt("courses.size()");
        System.out.println(numberOfCourse);

        // print purchaseAmount
        int totalAmount=jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //print title of the first course
        String titleFirstCourse=jsonPath.get("courses[2].title");
        System.out.println(titleFirstCourse);

        //Print All course titles and their respective Prices
        for (int i =0;i<numberOfCourse;i++){
            String allTitleName=jsonPath.get("courses["+i+"].title");
            String priseOfAllCourses = jsonPath.getString("courses["+i+"].price");
            System.out.println(allTitleName);
            System.out.println(priseOfAllCourses);
        }

        System.out.println("Print no of copies sold by RPA Course");
        for (int i =0;i<numberOfCourse;i++){
            String allTitleName=jsonPath.get("courses["+i+"].title");
           if (allTitleName.equalsIgnoreCase("RPA")){
               //copies sold
               int copiesOfRPA=jsonPath.get("courses["+i+"].copies");
               System.out.println(copiesOfRPA);
               break;
           }
        }
    }

}



