package ch.kplan.domain.ingredient;

import java.util.Objects;

public class IngredientSupply {

    private final Ingredient ingredient;

    private final Quantity requiredQuantity;

    private Quantity actualQuantity;

    private Quantity orderedQuantity;

    private IngredientSupply(IngredientSupplyBuilder builder) {
        this.ingredient = builder.ingredient;
        this.requiredQuantity = builder.requiredQuantity;
        this.actualQuantity = builder.actualQuantity;
        this.orderedQuantity = builder.orderedQuantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Quantity getRequiredQuantity() {
        return requiredQuantity;
    }

    public Quantity getActualQuantity() {
        return actualQuantity;
    }

    public Quantity getOrderedQuantity() {
        return orderedQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IngredientSupply that = (IngredientSupply) o;
        return ingredient == that.ingredient;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ingredient);
    }

    @Override
    public String toString() {
        return "IngredientSupply{" +
                "ingredient=" + ingredient +
                ", requiredQuantity=" + requiredQuantity +
                ", actualQuantity=" + actualQuantity +
                ", orderedQuantity=" + orderedQuantity +
                '}';
    }

    public void expend(Quantity quantity) {
        if (!quantity.unit().equals(this.actualQuantity.unit())) {
            throw new IllegalArgumentException("Quantity units do not match: " + this.actualQuantity.unit() + " != " + quantity.unit());
        }
        actualQuantity = new Quantity(quantity.unit(), actualQuantity.amount() - quantity.amount());
    }

    public void restock(Quantity quantity) {
        if (!quantity.unit().equals(this.actualQuantity.unit())) {
            throw new IllegalArgumentException("Quantity units do not match: " + this.actualQuantity.unit() + " != " + quantity.unit());
        }
        actualQuantity = new Quantity(quantity.unit(), actualQuantity.amount() + quantity.amount());
    }

    public static class IngredientSupplyBuilder {
        private Ingredient ingredient;
        private Quantity requiredQuantity;
        private Quantity actualQuantity;
        private Quantity orderedQuantity;


        public IngredientSupplyBuilder withIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        public IngredientSupplyBuilder withRequiredQuantity(Quantity requiredQuantity) {
            this.requiredQuantity = requiredQuantity;
            return this;
        }

        public IngredientSupplyBuilder withActualQuantity(Quantity actualQuantity) {
            this.actualQuantity = actualQuantity;
            return this;
        }

        public IngredientSupplyBuilder withOrderedQuantity(Quantity orderedQuantity) {
            this.orderedQuantity = orderedQuantity;
            return this;
        }

        public IngredientSupply build() {
            return new IngredientSupply(this);
        }
    }
}
