package ch.kplan.adapter.outgoing.ingredient;

import ch.kplan.domain.ingredient.Ingredient;
import ch.kplan.domain.ingredient.Quantity;
import ch.kplan.domain.ingredient.Supply;
import ch.kplan.domain.ingredient.Unit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmazonIngredientRestClientComponentMockserverTest {
    private MockWebServer mockWebServer;
    private AmazonIngredientRestClient restClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        restClient = new AmazonIngredientRestClient(new RestTemplate());
        String baseUrl = mockWebServer.url("").toString().replaceAll("/$", "");
        ReflectionTestUtils.setField(restClient, "baseUrl", baseUrl);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void order_successful() {
        String responseJson = """
            {
                "trackingNumber": "TRACK-1",
                "deliveryDate": "2025-08-29T12:00:00Z"
            }
        """;
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(responseJson));

        // Act
        Supply supply = restClient.order(Map.of(Ingredient.BEEF, new Quantity(Unit.KILOGRAM, 1)));

        // Assert
        assertThat(supply).isEqualTo(new Supply(LocalDate.of(2025, 8, 29), "TRACK-1"));
    }

    @Test
    void order_empty() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
                .addHeader("Content-Type", "application/json"));

        // Act + Assert
        assertThatThrownBy(() -> restClient.order(Map.of())).isInstanceOf(HttpClientErrorException.class);
    }
}