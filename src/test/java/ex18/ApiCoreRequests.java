package ex18;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;

public class ApiCoreRequests {

    private static final String BASE_URL = "https://playground.learnqa.ru/api/user/";

    // Метод для авторизации пользователя
    @Step("Авторизация пользователя с email: {0}")
    public Response loginUser(Map<String, String> loginData) {
        // Отправка POST-запроса для авторизации
        return RestAssured.given()
                .contentType("application/json")
                .body(loginData)  // Тело запроса содержит email и пароль
                .post("https://playground.learnqa.ru/api/user/login");
    }

    @Step("Отправка DELETE-запроса для удаления пользователя с ID: {0}")
    public Response deleteUser(String userId, String token, String cookie) {
        // Инициализируем запрос
        io.restassured.specification.RequestSpecification request = RestAssured.given()
                .contentType("application/json");

        // Добавляем токен в заголовок
        if (token != null) {
            request.header("x-csrf-token", token);
        }

        // Добавляем куки
        if (cookie != null) {
            request.cookie("auth_sid", cookie);
        }

        // Отправляем DELETE-запрос
        return request.delete(BASE_URL + userId);
    }

    // Метод для создания нового пользователя (если понадобится в тестах)
    @Step("Создание нового пользователя с данными: {0}")
    public Response createUser(Map<String, String> createData) {
        return RestAssured.given()
                .contentType("application/json")
                .body(createData)  // Тело запроса содержит все данные для нового пользователя
                .post("https://playground.learnqa.ru/api/user");
    }

    // Метод для получения данных пользователя
    @Step("Получение данных пользователя с ID: {0}")
    public Response getUserData(String userId, String token, String cookie) {
        io.restassured.specification.RequestSpecification request = RestAssured.given()
                .contentType("application/json");

        if (token != null) {
            request.header("x-csrf-token", token);
        }

        if (cookie != null) {
            request.cookie("auth_sid", cookie);
        }

        return request.get(BASE_URL + userId);
    }
}
