package CopmpexJson;

import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {
    @Test
    public void sumValidation() {
        int sum = 0;
        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());
        int count = jsonPath.getInt("courses.size()");
        for (int i =0;i<count;i++){
            int price=jsonPath.getInt("courses["+i+"].price");
            int copies=jsonPath.getInt("courses["+i+"].copies");
            int amount = price*copies;
            System.out.println(amount);
            sum = sum+amount;
        }
        int purchaseAmount=jsonPath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum,purchaseAmount);
    }
}
