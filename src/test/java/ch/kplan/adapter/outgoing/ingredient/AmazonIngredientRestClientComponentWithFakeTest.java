package ch.kplan.adapter.outgoing.ingredient;

import ch.kplan.domain.ingredient.Ingredient;
import ch.kplan.domain.ingredient.Quantity;
import ch.kplan.domain.ingredient.Supply;
import ch.kplan.domain.ingredient.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
        FakeAmazonIngredientController.class,
        AmazonIngredientRestClient.class,
        TomcatServletWebServerFactory.class,
        WebMvcAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class,
        JacksonAutoConfiguration.class})
class AmazonIngredientRestClientComponentWithFakeTest {
    @LocalServerPort
    private int port;

    @Autowired
    private AmazonIngredientRestClient restClient;

    @Autowired
    private FakeAmazonIngredientController fakeAmazonIngredientController;

    @BeforeEach
    void setUp() {
        restClient.setBaseUrl("http://localhost:" + port);
    }

    @Test
    void order_successful() {
        LocalDate fakeDeliveryDate = LocalDate.now();
        fakeAmazonIngredientController.setDeliveryDate(fakeDeliveryDate);
        fakeAmazonIngredientController.setTrackingNrCounter(1);

        // Act
        Supply supply = restClient.order(Map.of(Ingredient.BEEF, new Quantity(Unit.KILOGRAM, 1)));

        // Assert
        assertThat(supply).isEqualTo(new Supply(fakeDeliveryDate, "TRACK-1"));
    }

    @Test
    void order_empty() {
        // Act + Assert
        assertThatThrownBy(() -> restClient.order(Map.of())).isInstanceOf(HttpServerErrorException.class);
    }


}
