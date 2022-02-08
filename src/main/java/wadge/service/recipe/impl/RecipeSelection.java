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
        EVERYTHING, BY_DIFFICULTY, BY_RATING, USING_FRIDGE, BY_INGREDIENTS, BY_UNIT
    }

    // TODO -> refactor ?
    public RecipeSelection(final Set<Recipe> recipes) {
        this.recipes = recipes.stream().toList();
    }

	@Override
	public IRecipeSelection select(final Predicate<Recipe> predicate) {
		recipes = recipes.stream().filter(predicate).toList();
        return this;
	}

	@Override
	public List<Recipe> sort(final Comparator<Recipe> comparator) {
		return recipes.stream().sorted(comparator).toList();
	}    
}
