import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeWorkCookie {
    @Test
    public void homeWork (){
        Response responseGetAuth = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        assertEquals(200, responseGetAuth.statusCode(), "is not 200");
        System.out.println(responseGetAuth.cookies());

    }
}
