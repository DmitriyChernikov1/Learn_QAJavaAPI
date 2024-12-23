import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TokensTest {
    @Test
    public void TokenTest1(){
        Response response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();
        response.prettyPrint();
    }
    @Test
    public void TokenTest2(){

        JsonPath response = RestAssured
                .given()
                .queryParam("token", "token")
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String status = response.getString("status");

        if ("Job is ready".equals(status)) {
            System.out.println("Задача выполнена успешно!");
        } else {
            System.out.println("Статус задачи: " + status);
        }


    }
    @Test
    public void TokenTest3() throws InterruptedException {
        Response response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();
        Thread.sleep(10000);
        response.prettyPrint();

    }

}
