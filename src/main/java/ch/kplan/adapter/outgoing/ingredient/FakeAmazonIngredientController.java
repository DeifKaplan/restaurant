package ch.kplan.adapter.outgoing.ingredient;

import ch.kplan.domain.ingredient.Supply;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/amazon/ingredient")
public class FakeAmazonIngredientController {

    private int trackingNrCounter = 1;

    private LocalDate deliveryDate = LocalDate.now().plusDays(1L);

    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Supply order(@RequestBody AmazonIngredientRestClient.OrderRequest request) {
        // Beispiel: Liefere immer das erste bestellte Ingredient als geliefert zurück
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("OrderRequest muss mindestens ein Item enthalten");
        }
        return new Supply(deliveryDate, "TRACK-" + (trackingNrCounter++));
    }

    public void setTrackingNrCounter(int trackingNrCounter) {
        this.trackingNrCounter = trackingNrCounter;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}

