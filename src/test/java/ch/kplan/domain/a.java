package ch.kplan.domain;

import ch.kplan.domain.cook.Cook.CookBuilder;
import ch.kplan.domain.cook.CookId;
import ch.kplan.domain.ingredient.Ingredient;
import ch.kplan.domain.ingredient.Quantity;
import ch.kplan.domain.ingredient.Unit;
import ch.kplan.domain.meal.Meal.MealBuilder;
import ch.kplan.domain.meal.MealId;
import ch.kplan.domain.meal.MealIds;
import ch.kplan.domain.order.Order.OrderBuilder;
import ch.kplan.domain.order.OrderId;
import ch.kplan.domain.order.OrderStatus;
import ch.kplan.domain.table.TableId;
import ch.kplan.domain.waiter.Waiter.WaiterBuilder;
import ch.kplan.domain.waiter.WaiterId;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static ch.kplan.domain.table.Table.TableBuilder;
import static ch.kplan.util.DomainObjectHelper.createInstance;

public class a {

    private static int tableIdCounter = 1;

    private static int mealIdCounter = 1;

    private static int waiterIdCounter = 1;

    private static int orderIdCounter = 1;

    private static int cookIdCounter = 1;

    public static TableId TableId() {
        return new TableId(tableIdCounter++);
    }

    public static MealIds MealIds(MealId... mealIds) {
        return new MealIds(Collections.unmodifiableSet(new HashSet<>(Arrays.asList(mealIds))));
    }

    public static MealId MealId() {
        return new MealId(mealIdCounter++);
    }

    public static WaiterBuilder Waiter() {
        return createInstance(WaiterBuilder.class)
                .withName("Jane Doe")
                .withId(WaiterId());
    }

    public static WaiterId WaiterId() {
        return new WaiterId(waiterIdCounter++);
    }

    public static TableBuilder Table() {
        return createInstance(TableBuilder.class)
                .withId(TableId())
                .withNumberOfSeats(4);
    }

    public static OrderId OrderId() {
        return new OrderId(orderIdCounter++);
    }

    public static OrderBuilder Order() {
        return createInstance(OrderBuilder.class)
                .withId(OrderId())
                .withStatus(OrderStatus.PENDING)
                .withTableId(TableId())
                .withOrdered(ZonedDateTime.now())
                .withMealIds(MealIds(MealId()))
                .withWaiterId(WaiterId());
    }

    public static CookBuilder Cook() {
        return createInstance(CookBuilder.class)
                .withId(CookId());
    }

    public static CookId CookId() {
        return new CookId(cookIdCounter++);
    }

    public static MealBuilder Meal() {
        return createInstance(MealBuilder.class)
                .mealId(MealId())
                .name("Spaghetti Napoli")
                .addIngredient(Ingredient.CHEESE, new Quantity(Unit.GRAM, 30))
                .addIngredient(Ingredient.PASTA, new Quantity(Unit.GRAM, 100))
                .addIngredient(Ingredient.GARLIC, new Quantity(Unit.PIECE, 1))
                .addIngredient(Ingredient.ONION, new Quantity(Unit.PIECE, 1))
                .addIngredient(Ingredient.OIL, new Quantity(Unit.MILLILITER, 100))
                .addIngredient(Ingredient.SALT, new Quantity(Unit.GRAM, 5))
                .addIngredient(Ingredient.TOMATO, new Quantity(Unit.GRAM, 500));
    }
}
