import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserAgent{

    // Список User-Agent и ожидаемых значений
    private static final Object[][] userAgents = {

            // Новый User-Agent 1
            {
                    "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
                    "Android", "No", "Mobile"
            },
            // Новый User-Agent 2
            {
                    "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
                    "iOS", "Chrome", "Mobile"
            },
            // Новый User-Agent 3
            {
                    "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
                    "Unknown", "Unknown", "Googlebot"
            },
            // Новый User-Agent 4
            {
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
                    "No", "Chrome", "Web"
            },
            // Новый User-Agent 5
            {
                    "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
                    "iPhone", "No", "Mobile"
            }
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
