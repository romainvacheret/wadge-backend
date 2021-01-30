package wadge.service.recipe.api;

import java.util.List;

import wadge.model.recipe.Recipe;
import wadge.model.recipe.marmiton.MarmitonRecipe;

public interface IMarmitonConverter {
    List<MarmitonRecipe> getRecipesFromKeyword(List<String> keywords);
    List<Recipe> convertToRecipes(List<MarmitonRecipe> marmitonRecipes);
}
