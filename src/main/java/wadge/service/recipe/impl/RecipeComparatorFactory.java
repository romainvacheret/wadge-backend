package wadge.service.recipe.impl;

import java.util.Collections;
import java.util.Comparator;

import wadge.model.recipe.Recipe;
import wadge.service.recipe.impl.RecipeSelection.Parameter;

public class RecipeComparatorFactory {
    private RecipeComparatorFactory() {}

    public static Comparator<Recipe> getComparator(Parameter parameter) {
        Comparator<Recipe> rtr = null;

        switch(parameter) {
            case BY_DIFFICULTY:
                rtr = Comparator.comparingInt(Recipe::getDifficulty);
                break;

            case BY_RATING:
                rtr = Collections.reverseOrder(Comparator.comparingDouble(Recipe::getRating));
                break;

            case BY_INGREDIENTS:
                rtr = (r1, r2) -> Integer.compare(r1.getIngredients().size(), r2.getIngredients().size());
                break;

            default:
                rtr = Comparator.comparing(Recipe::getName);
        }
        return rtr;
    }     
}
