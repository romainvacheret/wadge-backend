package wadge.recipe.impl;

import java.util.List;
import java.util.Map;

public class Recipe {
    private String name;
    private List<String> steps;
    private int servings;
    private int preparation;
    private int difficulty;
    private String link;
    private List<Ingredient> ingredients;

    public Recipe() {}

    public Recipe(String name, List<String> steps, int servings, int preparation, int difficulty, String link, List<Ingredient> ingredients) {
        this.name = name;
        this.steps = steps;
        this.servings = servings;
        this.preparation = preparation;
        this.difficulty = difficulty;
        this.link = link;
        this.ingredients = ingredients;
    }

    public String getName() { return name; }
    public List<String> getSteps() { return steps; }
    public int getServings() { return servings; }
    public int getPreparation() { return preparation; }
    public int getDifficulty() { return difficulty; }
    public String getLink() { return link; }
    public List<Ingredient> getIngredients() { return ingredients; }

    @Override
    public String toString() {
        return "Recipe [difficulty=" + difficulty + ", ingredients=" + ingredients + ", link=" + link + ", name=" + name
                + ", preparation=" + preparation + ", servings=" + servings + ", steps=" + steps + "]";
    }

    
}