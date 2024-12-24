import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class PasswordTest {
    public void password(){
        Map<String,String> pass = new HashMap<>();
        pass.put("login", "secret_login");
        pass.put("password", "secret_pass");

        Response response = RestAssured
                .given()
                .body(pass)
                .when()
                .post("https://playground.learnqa.ru/api/get_secret_password_homework")
                .andReturn();
    }
}
