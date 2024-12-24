import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NewTokenTest {

    @Test
    public void testLongTimeJob() throws InterruptedException {
        // Шаг 1: Создание новой задачи
        Response createTaskResponse = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job");

        Map<String, Object> taskData = createTaskResponse.then()
                .contentType(ContentType.JSON)
                .extract().path("");

        String token = (String) taskData.get("token");
        int secondsToWait = (int) taskData.get("seconds");

        System.out.println(String.format("Токен задачи: %s, нужно подождать %d секунд.", token, secondsToWait));

        // Шаг 2: Проверка статуса до готовности задачи
        given().queryParam("token", token).get("https://playground.learnqa.ru/ajax/api/longtime_job").then()
                .contentType(ContentType.JSON)
                .body("status", equalTo("Job is NOT ready"));

        System.out.println("Проверка перед ожиданием завершена успешно!");

        // Шаг 3: Ожидание необходимого времени
        Thread.sleep(secondsToWait * 1000L);

        // Шаг 4: Проверка статуса после ожидания
        given().queryParam("token", token).get("https://playground.learnqa.ru/ajax/api/longtime_job").then()
                .contentType(ContentType.JSON)
                .body("status", equalTo("Job is ready"))
                .body("result", notNullValue());

        System.out.println("Проверка после ожидания завершена успешно!");
    }
}
