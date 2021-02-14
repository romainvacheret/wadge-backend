package wadge.service.recipe.api;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import wadge.model.recipe.Recipe;

public interface IRecipeSelection {
    IRecipeSelection select(Predicate<Recipe> predicate);
    List<Recipe> sort(Comparator<Recipe> comparator);
}
