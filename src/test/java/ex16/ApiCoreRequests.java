package ex16;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class ApiCoreRequests {

    private static final String BASE_URL = "https://playground.learnqa.ru/api/user/";

    @Step("Авторизация пользователя с данными: {0}")
    public Response loginUser(Map<String, String> authData) {
        return RestAssured.given()
                .contentType("application/json")
                .body(authData)
                .post(BASE_URL + "login");
    }

    @Step("Получение данных пользователя с ID: {0} с авторизацией")
    public Response getUserDataWithAuth(String userId, String token, String cookie) {
        return RestAssured.given()
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .get(BASE_URL + userId);
    }
}
