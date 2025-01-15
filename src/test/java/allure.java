package ex6;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class allure {

    private static final String BASE_URL = "https://playground.learnqa.ru/api/long_redirect";

    @Test
    @io.qameta.allure.Description("Test to print the redirected URL from the given URL")
    public void testRedirectUrl() {
        String redirectedUrl = getRedirectedUrl(BASE_URL);
        System.out.println("Redirected to URL: " + redirectedUrl);
    }

    @Step("Get the redirected URL for the given URL: {baseUrl}")
    private String getRedirectedUrl(String baseUrl) {
        Response response = given()
                .redirects().follow(false) // Disable automatic redirection
                .when()
                .get(baseUrl);

        response.then().statusCode(301); // Verify the response status for redirection
        return response.getHeader("Location");
    }
}
