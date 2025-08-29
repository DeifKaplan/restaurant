package ch.kplan.domain.ingredient;

import java.time.LocalDate;

public record Supply(LocalDate deliveryDate, String trackingNumber) {
}
