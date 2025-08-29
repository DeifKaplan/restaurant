package ch.kplan.domain.ingredient;

public class IngredientNotAvailableException extends Exception {
    public IngredientNotAvailableException(String message) {
        super(message);
    }
}
