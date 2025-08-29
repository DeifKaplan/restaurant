package ch.kplan.application;

import ch.kplan.domain.meal.MealIds;
import ch.kplan.domain.meal.MealRepository;
import ch.kplan.domain.meal.Meals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealService {

    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meals listAvailableMeals() {
        return mealRepository.getAll();
    }

    public Meals getMealsById(MealIds mealIds) {
        return mealRepository.getAllById(mealIds);
    }
}
