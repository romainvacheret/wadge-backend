package wadge.service.recipe.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.service.recipe.api.RecipeSelection;

public class UserListSelection implements RecipeSelection {
    private List<Recipe> recipes;
    private List<String> userFoodList;
    private List<Recipe> userRecipe;

    public UserListSelection(List<Recipe> recipes, List<String> userFoodList) {
        this.recipes = recipes;
        this.userFoodList = userFoodList;
        this.userRecipe = new ArrayList<>();
    }
    
    public Predicate<Ingredient> isInList = i -> userFoodList.contains(i.getName());

    @Override
    public RecipeSelection select() {
        recipes.forEach(recipe -> {
            List<Ingredient> listRecipe = recipe.getIngredients();
            List<Ingredient> listI = listRecipe.stream().filter(isInList).collect(Collectors.toList());
            List<String> list2 = new ArrayList<>();
            for (Ingredient ingredient : listI) {
                list2.add(ingredient.getName());
            }
            if(list2.equals(userFoodList)){
                userRecipe.add(recipe);
            }
        });
        return this;
    }

    @Override
    public List<Recipe> sort() {
        return userRecipe;
    }
    
}
