
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

public class RedirectTest {

    @Test
    public void testRestAssured(){

        Response response = RestAssured
                .given()

                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String locationHeader = response.getHeader("location");
        System.out.println(locationHeader);


    }
}
