package ch.kplan.adapter.outgoing.ingredient;

import ch.kplan.domain.ingredient.Ingredient;
import ch.kplan.domain.ingredient.Quantity;
import ch.kplan.domain.ingredient.Supply;
import ch.kplan.domain.ingredient.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        FakeAmazonIngredientController.class,
        AmazonIngredientRestClient.class,
        TomcatServletWebServerFactory.class,
        WebMvcAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class,
        JacksonAutoConfiguration.class})
@ActiveProfiles("test")
class AmazonIngredientRestClientComponentTest {
    @LocalServerPort
    private int port;

    @Autowired
    private AmazonIngredientRestClient restClient;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        restClient.setBaseUrl("http://localhost:" + port);
    }

    @Test
    void order_shouldCallFakeServerAndReturnSupply() {

        // Act
        Supply supply = restClient.order(Map.of(Ingredient.BEEF, new Quantity(Unit.KILOGRAM, 1)));

        // Assert
        assertThat(supply).isNotNull();
    }
}
