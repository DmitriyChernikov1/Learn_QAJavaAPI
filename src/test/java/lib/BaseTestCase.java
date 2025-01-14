package lib;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

public class BaseTestCase {

    protected RequestSpecification request;

    @BeforeEach
    public void setUp() {
        // Настройка базового URL для RestAssured
        RestAssured.baseURI = "https://playground.learnqa.ru/api/";

        // Инициализация спецификации запроса
        request = RestAssured.given()
                .contentType("application/json");
    }

    @AfterEach
    public void tearDown() {
        // Любая очистка после теста, если нужно
    }
}
