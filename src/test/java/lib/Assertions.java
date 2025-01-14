package lib;

import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

public class Assertions {

    // Проверка, что статус код ответа равен 200
    public static void assertStatusCode200(Response response) {
        assertEquals(200, response.getStatusCode(), "Expected status code 200, but got " + response.getStatusCode());
    }

    // Проверка, что ответ содержит строку
    public static void assertResponseContains(Response response, String expectedValue) {
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(expectedValue), "Response does not contain expected value: " + expectedValue);
    }

    // Проверка, что ответ содержит указанный ключ
    public static void assertResponseContainsKey(Response response, String key) {
        assertTrue(response.jsonPath().get(key) != null, "Response does not contain key: " + key);
    }
}
