package ch.kplan.domain.meal;

public interface MealRepository {
    Meals getAll();

    Meals getAllById(MealIds mealIds);
}
