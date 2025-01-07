
import io.restassured.path.json.JsonPath;


import org.junit.jupiter.api.Assertions;

import io.restassured.RestAssured;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestShort {

    @ParameterizedTest
    @ValueSource(strings = {"hello, world", "abracadabraeveryday"})
    public void TestHelloWithoutName(String name){
        Map<String, String> queryParams = new HashMap<>();

        if(name.length() > 15){
            queryParams.put("name", name);
        }
        else {
            Assertions.fail("Текст слишком короткий"); // Тест падает с ошибкой

        }
        JsonPath response = RestAssured
                .given()
                .queryParams(queryParams)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = response.getString("answer");
        String expectedName = (name.length() > 15)  ? name :"someone";
        assertEquals("Hello, " + expectedName, answer, "The answer is not expected");
    }

}
