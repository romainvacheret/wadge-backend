package wadge.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeBuilder {
    private String name;
    private List<String> steps;
    private Object quantity;
    private List<Map<String, Object>> ingredients;

    public RecipeBuilder(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
        this.ingredients = new ArrayList<>();
    } 

    public RecipeBuilder withSteps(List<String> steps) {
        this.steps.addAll(steps);
        return this;
    }
    
    public RecipeBuilder withQuantity(Object quantity) {
        this.quantity = quantity;
        return this;
    }

    public RecipeBuilder withIngredients(List<Map<String, Object>> ingredients) {
        this.ingredients.addAll(ingredients);
        return this;
    }

    public Recipe generate() {
        return new Recipe(name, steps, quantity, ingredients);
    }


}
