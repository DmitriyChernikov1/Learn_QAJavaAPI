package ex15;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiCoreRequests {

    private static final String BASE_URL = "https://playground.learnqa.ru/api/user/";

    @Step("Создание пользователя с данными: {0}")
    public Response createUser(String name, String email, String password) {
        return RestAssured.given()
                .contentType("application/json")
                .body("{ \"name\": \"" + name + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .post(BASE_URL);
    }
}
