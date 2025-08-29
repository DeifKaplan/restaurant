package ch.kplan.application.ingredient;

import ch.kplan.domain.ingredient.IngredientSupplyRepository;
import ch.kplan.domain.ingredient.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    @Autowired
    private IngredientSupplyRepository ingredientSupplyRepository;

    public Supply orderExpendedIngredients() {
        return null;
    }

}
