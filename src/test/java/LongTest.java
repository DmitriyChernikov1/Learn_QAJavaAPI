import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class LongTest {

    @Test
    public void longTest() {
        // Начальный URL
        String url = "https://playground.learnqa.ru/api/long_redirect";
        int redirectCounter = 0;

        while (true) {
            Response response = RestAssured
            .given()
            .redirects()
            .follow(false)
            .when().get(url)
            .andReturn();

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                break;
            }

            String newUrl = response.header("Location");

            redirectCounter++;

            url = newUrl;
        }

        System.out.println("Количество редиректов: " + redirectCounter);
        System.out.println("Конечный URL: " + url);
    }
}
