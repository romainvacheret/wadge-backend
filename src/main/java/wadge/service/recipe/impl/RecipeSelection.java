package wadge.service.recipe.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import wadge.model.recipe.Recipe;
import wadge.service.recipe.api.IRecipeSelection;

public class RecipeSelection implements IRecipeSelection {
    private List<Recipe> recipes;

    public enum Parameter {
        BY_DIFFICULTY, BY_RATING, USING_FRIDGE, EVERYTHING,FAVORITE,REALISE
    }

    public RecipeSelection(Set<Recipe> recipes) {
        this.recipes = recipes.stream().collect(Collectors.toList());
    }

	@Override
	public IRecipeSelection select(Predicate<Recipe> predicate) {
		recipes = recipes.stream().filter(predicate).collect(Collectors.toList());
        return this;
	}

	@Override
	public List<Recipe> sort(Comparator<Recipe> comparator) {
		return recipes.stream().sorted(comparator).collect(Collectors.toList());
	}    
}
