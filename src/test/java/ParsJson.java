import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
public class ParsJson {
    @Test
    public void testRestAssured(){

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();
        response.print();

    }
}
