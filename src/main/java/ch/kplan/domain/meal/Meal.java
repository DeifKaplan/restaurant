package ch.kplan.domain.meal;

import ch.kplan.domain.event.DomainEventPublisher;
import ch.kplan.domain.ingredient.Ingredient;
import ch.kplan.domain.ingredient.IngredientUsed;
import ch.kplan.domain.ingredient.Quantity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Meal {

    private final MealId mealId;

    private final String name;

    private final Map<Ingredient, Quantity> ingredients;

    private Meal(MealBuilder builder) {
        this.mealId = builder.mealId;
        this.name = builder.name;
        this.ingredients = builder.ingredients;
    }

    public MealId getMealId() {
        return mealId;
    }

    public String getName() {
        return name;
    }

    public void cook(final DomainEventPublisher domainEventPublisher) {
        ingredients.forEach((ingredient, quantity) -> domainEventPublisher.publish(new IngredientUsed(ingredient, quantity)));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(mealId, meal.mealId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mealId);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealId=" + mealId +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public static class MealBuilder {
        private MealId mealId;
        private String name;
        private Map<Ingredient, Quantity> ingredients = new HashMap<>();

        public MealBuilder mealId(MealId mealId) {
            this.mealId = mealId;
            return this;
        }

        public MealBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MealBuilder addIngredient(Ingredient ingredient, Quantity quantity) {
            ingredients.put(ingredient, quantity);
            return this;
        }

        public Meal build() {
            return new Meal(this);
        }
    }
}
