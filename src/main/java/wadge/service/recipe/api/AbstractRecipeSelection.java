package wadge.service.recipe.api;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;

import wadge.model.recipe.Recipe;

public abstract class AbstractRecipeSelection {
    protected Set<Recipe> recipes;
    protected List<Map.Entry<Integer, Recipe>> scores;

    public abstract AbstractRecipeSelection compute(final ToIntFunction<Recipe> func);
    public abstract AbstractRecipeSelection filter(final IntPredicate predicate);
    public abstract List<Recipe> sort(final Comparator<Map.Entry<Integer, Recipe>> comparator);
}
