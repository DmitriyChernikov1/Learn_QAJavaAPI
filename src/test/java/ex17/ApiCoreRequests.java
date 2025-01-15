package ex17;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class ApiCoreRequests {
    private static final String BASE_URL = "https://example.com/api/user/";

    @Step("Отправка PUT-запроса на обновление пользователя с ID: {0}")
    public Response updateUser(String userId, Map<String, String> updateData, String token, String cookie) {
        // Инициализируем запрос
        io.restassured.specification.RequestSpecification request = RestAssured.given()
                .contentType("application/json")
                .body(updateData);

        // Проверяем и добавляем токен
        if (token != null) {
            request.header("x-csrf-token", token);
        }

        // Проверяем и добавляем куки
        if (cookie != null) {
            request.cookie("auth_sid", cookie);
        }

        // Отправляем PUT-запрос
        return request.put(BASE_URL + userId);
    }
}
