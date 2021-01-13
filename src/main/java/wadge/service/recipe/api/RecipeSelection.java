package wadge.service.recipe.api;

import java.util.List;

import wadge.model.recipe.Recipe;

public interface RecipeSelection {
    RecipeSelection select();
    List<Recipe> sort();
}
