package ex15;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

public class UserRegisterTest {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void testCreateUserWithInvalidEmail() {
        String invalidEmail = "email.com";  // Некорректный email (нет символа @)
        Response response = apiCoreRequests.createUser("TestUser", invalidEmail, "TestPassword");

        assertEquals(400, response.getStatusCode(), "Ожидаемый статус: 400");
        assertTrue(response.body().asString().contains("Invalid email format"), "Ответ должен содержать сообщение об ошибке формата email");
    }

    @ParameterizedTest
    @MethodSource("provideMissingFieldData")
    public void testCreateUserWithMissingField(String name, String email, String password) {
        Response response = apiCoreRequests.createUser(name, email, password);

        assertEquals(400, response.getStatusCode(), "Ожидаемый статус: 400");
        assertTrue(response.body().asString().contains("The following required fields are missing"), "Ответ должен содержать сообщение об отсутствующих обязательных полях");
    }

    // Метод для параметризованных тестов на отсутствие полей
    private static Stream<Arguments> provideMissingFieldData() {
        return Stream.of(
                Arguments.of(null, "test@example.com", "TestPassword"),  // Отсутствует имя
                Arguments.of("TestUser", null, "TestPassword"),          // Отсутствует email
                Arguments.of("TestUser", "test@example.com", null)      // Отсутствует пароль
        );
    }

    @Test
    public void testCreateUserWithShortName() {
        String shortName = "A";  // Имя длиной в 1 символ
        Response response = apiCoreRequests.createUser(shortName, "test@example.com", "TestPassword");

        assertEquals(400, response.getStatusCode(), "Ожидаемый статус: 400");
        assertTrue(response.body().asString().contains("The name must be between 2 and 250 characters"), "Ответ должен содержать сообщение о некорректной длине имени");
    }

    @Test
    public void testCreateUserWithLongName() {
        String longName = new String(new char[251]).replace('\0', 'A');  // Имя длиной более 250 символов
        Response response = apiCoreRequests.createUser(longName, "test@example.com", "TestPassword");

        assertEquals(400, response.getStatusCode(), "Ожидаемый статус: 400");
        assertTrue(response.body().asString().contains("The name must be between 2 and 250 characters"), "Ответ должен содержать сообщение о некорректной длине имени");
    }
}
