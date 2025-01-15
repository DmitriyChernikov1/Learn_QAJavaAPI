package ex18;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserDeleteTest {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    // Данные для авторизации
    private final String email = "vinkotov@example.com";
    private final String password = "1234";

    // Тест 1: Негативный тест - удаление чужого пользователя
    @Test
    @DisplayName("Попытка удалить пользователя с ID 2, будучи авторизованными под другим пользователем")
    public void testDeleteUserAsAnotherUser() {
        String userIdToDelete = "2";

        // Авторизация под другим пользователем
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", email);
        loginData.put("password", password);

        Response loginResponse = apiCoreRequests.loginUser(loginData);
        String token = loginResponse.getHeader("x-csrf-token");
        String cookie = loginResponse.getCookie("auth_sid");

        // Попытка удалить другого пользователя
        Response deleteResponse = apiCoreRequests.deleteUser(userIdToDelete, token, cookie);

        assertEquals(403, deleteResponse.getStatusCode(), "Ожидаемый статус: 403");
        assertTrue(deleteResponse.body().asString().contains("You are not authorized to delete this user"), "Ожидаемое сообщение об ошибке");
    }

    // Тест 2: Позитивный тест - создание, удаление и проверка удаления пользователя
    @Test
    @DisplayName("Позитивный тест - создание пользователя, удаление и проверка его удаления")
    public void testCreateAndDeleteUser() {
        // Создание нового пользователя
        Map<String, String> createData = new HashMap<>();
        createData.put("email", "testuser@example.com");
        createData.put("password", "password123");
        createData.put("firstName", "Test");
        createData.put("lastName", "User");

        Response createResponse = apiCoreRequests.createUser(createData);
        String newUserId = createResponse.jsonPath().getString("id");

        // Авторизация под только что созданным пользователем
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", "testuser@example.com");
        loginData.put("password", "password123");

        Response loginResponse = apiCoreRequests.loginUser(loginData);
        String token = loginResponse.getHeader("x-csrf-token");
        String cookie = loginResponse.getCookie("auth_sid");

        // Удаление пользователя
        Response deleteResponse = apiCoreRequests.deleteUser(newUserId, token, cookie);
        assertEquals(200, deleteResponse.getStatusCode(), "Ожидаемый статус: 200");

        // Попытка получить данные удаленного пользователя
        Response getResponse = apiCoreRequests.getUserData(newUserId, token, cookie);
        assertEquals(404, getResponse.getStatusCode(), "Ожидаемый статус: 404");
        assertTrue(getResponse.body().asString().contains("User not found"), "Ожидаемое сообщение об ошибке");
    }

    // Тест 3: Негативный тест - попытка удалить пользователя, будучи авторизованным другим пользователем
    @Test
    @DisplayName("Попытка удалить пользователя, будучи авторизованными другим пользователем")
    public void testDeleteUserAsDifferentUser() {
        String userIdToDelete = "3";  // Пример ID другого пользователя

        // Авторизация под другим пользователем
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", "vinkotov@example.com");
        loginData.put("password", "1234");

        Response loginResponse = apiCoreRequests.loginUser(loginData);
        String token = loginResponse.getHeader("x-csrf-token");
        String cookie = loginResponse.getCookie("auth_sid");

        // Попытка удалить чужого пользователя
        Response deleteResponse = apiCoreRequests.deleteUser(userIdToDelete, token, cookie);

        assertEquals(403, deleteResponse.getStatusCode(), "Ожидаемый статус: 403");
        assertTrue(deleteResponse.body().asString().contains("You are not authorized to delete this user"), "Ожидаемое сообщение об ошибке");
    }
}
