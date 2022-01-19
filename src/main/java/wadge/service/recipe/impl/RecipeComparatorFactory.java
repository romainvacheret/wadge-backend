package wadge.service.recipe.impl;

import java.util.Collections;
import java.util.Comparator;

import wadge.model.recipe.Recipe;
import wadge.service.recipe.impl.RecipeSelection.Parameter;

public class RecipeComparatorFactory {
    private RecipeComparatorFactory() {}

    public static Comparator<Recipe> getComparator(final Parameter parameter) {
        return switch(parameter) {
            case BY_DIFFICULTY -> Comparator.comparingInt(Recipe::getDifficulty);
            case BY_RATING -> Collections.reverseOrder(Comparator.comparingDouble(Recipe::getRating));
            case BY_INGREDIENTS -> (r1, r2) -> Integer.compare(r1.getIngredients().size(), r2.getIngredients().size());
            default -> Comparator.comparing(Recipe::getName);
        };
    }
}
