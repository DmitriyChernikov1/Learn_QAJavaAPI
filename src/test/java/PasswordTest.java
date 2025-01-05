import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class PasswordTest {
    @Test
    public void password(){
        Map <String, Object> data = new HashMap<>();
        data.put("login","super_admin");
        data.put("password", "secret_pass");
        Response responseForGet = RestAssured
                .given()
                .body(data)
                .when()
                .post("https://playground.learnqa.ru/api/get_secret_password_homework")
                .andReturn();
       String responseCookie = responseForGet.getCookie("auth_cookie");
       Map<String, String>cookies = new HashMap<>();
       cookies.put("auth_cookie", responseCookie);
       Response responseForCheck = RestAssured
               .given()
               .body(data)
               .body(cookies)
               .when()
               .post(" https://playground.learnqa.ru/ajax/api/check_auth_cookie")
               .andReturn();

        responseForCheck.print();
    }
}
