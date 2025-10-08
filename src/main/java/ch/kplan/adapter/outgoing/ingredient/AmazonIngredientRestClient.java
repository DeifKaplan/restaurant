package ch.kplan.adapter.outgoing.ingredient;

import ch.kplan.domain.ingredient.Ingredient;
import ch.kplan.domain.ingredient.IngredientGateway;
import ch.kplan.domain.ingredient.Quantity;
import ch.kplan.domain.ingredient.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AmazonIngredientRestClient implements IngredientGateway {

    private final RestTemplate restTemplate;

    @Autowired
    public AmazonIngredientRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${amazon.ingredient.api.url:http://localhost:8081}")
    private String baseUrl;

    @Override
    public Supply order(Map<Ingredient, Quantity> ingredientsToOrder) {
        OrderRequest request = new OrderRequest();
        for (Map.Entry<Ingredient, Quantity> entry : ingredientsToOrder.entrySet()) {
            request.addItem(new OrderItem(entry.getKey(), entry.getValue()));
        }
        String url = baseUrl + "/amazon/ingredient/order";
        ResponseEntity<Supply> response = restTemplate.postForEntity(url, request, Supply.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Amazon API call failed: " + response.getStatusCode());
        }
        return response.getBody();
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // --- DTOs für Request/Response ---
    public static class OrderRequest {
        private java.util.List<OrderItem> items = new java.util.ArrayList<>();
        public OrderRequest() {}
        public java.util.List<OrderItem> getItems() { return items; }
        public void setItems(java.util.List<OrderItem> items) { this.items = items; }
        public void addItem(OrderItem item) { this.items.add(item); }
    }
    public static class OrderItem {
        private Ingredient ingredient;
        private Quantity quantity;
        public OrderItem() {}
        public OrderItem(Ingredient ingredient, Quantity quantity) {
            this.ingredient = ingredient;
            this.quantity = quantity;
        }
        public Ingredient getIngredient() { return ingredient; }
        public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }
        public Quantity getQuantity() { return quantity; }
        public void setQuantity(Quantity quantity) { this.quantity = quantity; }
    }
}
