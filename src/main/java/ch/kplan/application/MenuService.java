package ch.kplan.application;

import ch.kplan.domain.meal.MealRepository;
import ch.kplan.domain.meal.Meals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final MealRepository mealRepository;

    @Autowired
    public MenuService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meals listMeals() {
        return mealRepository.getAll();
    }
}
