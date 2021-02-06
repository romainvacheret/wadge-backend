package wadge.service.recipe.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;

import wadge.model.recipe.Recipe;
import wadge.service.recipe.api.AbstractRecipeSelection;

public class SelectionWithDifficulty extends AbstractRecipeSelection {

    @Override
    public AbstractRecipeSelection compute(ToIntFunction<Recipe> func) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractRecipeSelection filter(IntPredicate predicate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Recipe> sort(Comparator<Entry<Integer, Recipe>> comparator) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
