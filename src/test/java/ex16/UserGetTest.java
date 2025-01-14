package ex16;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserGetTest {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void testGetAnotherUserData() {
        // Авторизация пользователя
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");


        Response loginResponse = apiCoreRequests.loginUser(authData);


        String token = loginResponse.header("x-csrf-token");
        String cookie = loginResponse.cookie("auth_sid");


        String anotherUserId = "3";
        Response userResponse = apiCoreRequests.getUserDataWithAuth(anotherUserId, token, cookie);


        assertEquals(200, userResponse.getStatusCode(), "Ожидаемый статус: 200");
        Map<String, Object> responseData = userResponse.jsonPath().getMap("$");


        assertTrue(responseData.containsKey("username"), "Ответ должен содержать поле 'username'");
        assertEquals(1, responseData.size(), "Ответ должен содержать только одно поле");
    }
}
