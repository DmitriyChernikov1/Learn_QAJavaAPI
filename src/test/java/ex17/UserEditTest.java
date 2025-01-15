package ex17;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserEditTest {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @DisplayName("Попытка изменить данные пользователя без авторизации")
    public void testEditUserWithoutAuth() {
        String userId = "350";

        Map<String, String> updateData = new HashMap<>();
        updateData.put("firstName", "UnauthorizedChange");

        Response response = apiCoreRequests.updateUser(userId, updateData, null, null);

        assertEquals(400, response.getStatusCode(), "Ожидаемый статус: 400");
        assertTrue(response.body().asString().contains("Auth token not supplied"), "Ожидаемое сообщение об ошибке");
    }

    @Test
    @DisplayName("Попытка изменить данные пользователя, будучи авторизованным другим пользователем")
    public void testEditUserAsAnotherUser() {
        String userId = "350";

        Map<String, String> updateData = new HashMap<>();
        updateData.put("firstName", "HackedName");

        // Авторизация другого пользователя
        String otherUserToken = "valid_token_for_other_user";
        String otherUserCookie = "valid_cookie_for_other_user";

        Response response = apiCoreRequests.updateUser(userId, updateData, otherUserToken, otherUserCookie);

        assertEquals(403, response.getStatusCode(), "Ожидаемый статус: 403");
        assertTrue(response.body().asString().contains("You are not authorized"), "Ожидаемое сообщение об ошибке");
    }

    @Test
    @DisplayName("Попытка изменить email пользователя на некорректный (без @)")
    public void testEditEmailToInvalid() {
        String userId = "350";

        Map<String, String> updateData = new HashMap<>();
        updateData.put("email", "invalidemail.com");

        // Авторизация того же пользователя
        String userToken = "valid_token_for_same_user";
        String userCookie = "valid_cookie_for_same_user";

        Response response = apiCoreRequests.updateUser(userId, updateData, userToken, userCookie);

        assertEquals(400, response.getStatusCode(), "Ожидаемый статус: 400");
        assertTrue(response.body().asString().contains("Invalid email format"), "Ожидаемое сообщение об ошибке");
    }

    @Test
    @DisplayName("Попытка изменить firstName на слишком короткое значение")
    public void testEditFirstNameToShort() {
        String userId = "35";

        Map<String, String> updateData = new HashMap<>();
        updateData.put("firstName", "A");

        // Авторизация того же пользователя
        String userToken = "valid_token_for_same_user";
        String userCookie = "valid_cookie_for_same_user";

        Response response = apiCoreRequests.updateUser(userId, updateData, userToken, userCookie);

        assertEquals(400, response.getStatusCode(), "Ожидаемый статус: 400");
        assertTrue(response.body().asString().contains("First name is too short"), "Ожидаемое сообщение об ошибке");
    }
}
