package ch.kplan.domain.ingredient;

import java.util.Map;

public interface IngredientGateway {

    Supply order(Map<Ingredient, Quantity> ingredientsToOrder) throws IngredientNotAvailableException;
}
