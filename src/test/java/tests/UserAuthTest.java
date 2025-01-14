package tests;

import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.Assertions;
import org.junit.jupiter.api.Test;

public class UserAuthTest extends BaseTestCase {

    @Test
    public void testCreateUser() {
        // Данные для создания пользователя
        String username = "testuser123";
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@example.com";
        String password = "Password123";

        // Создаем Map для данных пользователя
        String requestBody = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"firstName\": \"" + firstName + "\",\n" +
                "  \"lastName\": \"" + lastName + "\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";

        // Отправляем POST-запрос для создания пользователя
        Response response = request
                .body(requestBody)
                .post("user/");

        // Проверки
        Assertions.assertStatusCode200(response);  // Проверка, что статус код 200
        Assertions.assertResponseContains(response, username);  // Проверка, что ответ содержит username
        Assertions.assertResponseContains(response, email);  // Проверка, что ответ содержит email
    }
}
