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

            default:
                rtr = Comparator.comparing(Recipe::getName);
        }
        return rtr;
    }     
}
