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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AmazonIngredientRestClientUnitTest {

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private AmazonIngredientRestClient restClient;

    @Test
    void order_successful() {
        AmazonIngredientRestClient.OrderRequest orderRequest = new AmazonIngredientRestClient.OrderRequest();
        orderRequest.addItem(
                new AmazonIngredientRestClient.OrderItem(Ingredient.BEEF, new Quantity(Unit.KILOGRAM, 10)));
        when(restTemplateMock.postForEntity(eq("null/amazon/ingredient/order"), refEq(orderRequest), eq(Supply.class)))
                .thenReturn(new org.springframework.http.ResponseEntity<>(
                        new Supply(LocalDate.of(2025, 8, 29), "TRACK-1"),
                        org.springframework.http.HttpStatus.OK));

        // Act
        Supply supply = restClient.order(Map.of(Ingredient.BEEF, new Quantity(Unit.KILOGRAM, 1)));

        // Assert
        assertThat(supply).isEqualTo(new Supply(LocalDate.of(2025, 8, 29), "TRACK-1"));
    }

    @Test
    void order_empty() {
        when(restTemplateMock.postForEntity(eq("null/amazon/ingredient/order"), any(), eq(Supply.class)))
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST));

        // Act + Assert
        assertThatThrownBy(() -> restClient.order(Map.of())).isInstanceOf(HttpClientErrorException.class);
    }
}