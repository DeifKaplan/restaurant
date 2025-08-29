package ch.kplan.domain.ingredient;

public interface IngredientSupplyRepository {

    IngredientSupply get(Ingredient ingredient);

    void update(IngredientSupply ingredientSupply);
}
