import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserAgent {

    // Список User-Agent и ожидаемых значений
    private static final Object[][] userAgents = {
            {
                    "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/537.36 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/537.36",
                    "iOS", "Safari", "mobile"
            },
            {
                    "Mozilla/5.0 (Android 10; Mobile; rv:79.0) Gecko/79.0 Firefox/79.0",
                    "Android", "Firefox", "mobile"
            },
            {
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
                    "Unknown", "Chrome", "web"
            }
            // Добавьте остальные User-Agent и ожидаемые значения из списка по ссылке
    };

    @Test
    public void testUserAgents() {
        List<String> incorrectUserAgents = new ArrayList<>();

        for (Object[] userAgentData : userAgents) {
            String userAgent = (String) userAgentData[0];
            String expectedDevice = (String) userAgentData[1];
            String expectedBrowser = (String) userAgentData[2];
            String expectedPlatform = (String) userAgentData[3];

            Response response = sendRequestWithUserAgent(userAgent);

            String device = response.jsonPath().getString("device");
            String browser = response.jsonPath().getString("browser");
            String platform = response.jsonPath().getString("platform");

            List<String> incorrectFields = new ArrayList<>();

            if (!device.equals(expectedDevice)) {
                incorrectFields.add("device (expected " + expectedDevice + ", got " + device + ")");
            }
            if (!browser.equals(expectedBrowser)) {
                incorrectFields.add("browser (expected " + expectedBrowser + ", got " + browser + ")");
            }
            if (!platform.equals(expectedPlatform)) {
                incorrectFields.add("platform (expected " + expectedPlatform + ", got " + platform + ")");
            }

            if (!incorrectFields.isEmpty()) {
                incorrectUserAgents.add("User-Agent: " + userAgent + "\n" + String.join("\n", incorrectFields));
            }
        }

        // Выводим список некорректных User-Agent
        if (!incorrectUserAgents.isEmpty()) {
            System.out.println("Некорректные User-Agent:");
            for (String incorrectUserAgent : incorrectUserAgents) {
                System.out.println(incorrectUserAgent);
            }
        } else {
            System.out.println("Все User-Agent верны.");
        }
    }

    // Метод отправки GET-запроса с заголовком User-Agent
    private Response sendRequestWithUserAgent(String userAgent) {
        return RestAssured.given()
                .header("User-Agent", userAgent)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check");
    }
}
