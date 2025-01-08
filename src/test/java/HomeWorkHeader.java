import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeWorkHeader {
    @Test
    public void headersTest() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        // Проверяем наличие заголовка X-Secret-Homework-Header
        assertTrue(response.getHeaders().hasHeaderWithName("X-Secret-Homework-Header"), "Заголовок отсутствует");

        // Проверяем значение заголовка
        String headerValue = response.getHeader("X-Secret-Homework-Header");
        assertEquals("Some secret value", headerValue, "Значение заголовка некорректно");

        // Выводим все куки из ответа
        System.out.println(response.getDetailedCookies());
    }
}
