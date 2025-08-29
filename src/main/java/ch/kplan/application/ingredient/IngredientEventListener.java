package ch.kplan.application.ingredient;

import ch.kplan.domain.ingredient.IngredientDelivered;
import ch.kplan.domain.ingredient.IngredientSupply;
import ch.kplan.domain.ingredient.IngredientSupplyRepository;
import ch.kplan.domain.ingredient.IngredientUsed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class IngredientEventListener {

    @Autowired
    private IngredientSupplyRepository ingredientSupplyRepository;

    @EventListener
    public void handle(IngredientUsed event) {
        IngredientSupply ingredientSupply = ingredientSupplyRepository.get(event.ingredient());
        ingredientSupply.expend(event.quantity());
        ingredientSupplyRepository.update(ingredientSupply);
    }
    @EventListener
    public void handle(IngredientDelivered event) {
        IngredientSupply ingredientSupply = ingredientSupplyRepository.get(event.ingredient());
        ingredientSupply.restock(event.quantity());
        ingredientSupplyRepository.update(ingredientSupply);
    }

}
