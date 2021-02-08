package wadge.model.recipe;

import java.util.List;

public class Recipe {
    private String name;
    private List<String> steps;
    private int servings;
    private int preparation;
    private int difficulty;
    private double rating;
    private String link;
    private List<Ingredient> ingredients;

    public Recipe() {}

    public Recipe(String name, List<String> steps, int servings, int preparation, int difficulty, double rating, String link, List<Ingredient> ingredients) {
        this.name = name;
        this.steps = steps;
        this.servings = servings;
        this.preparation = preparation;
        this.difficulty = difficulty;
        this.rating = rating;
        this.link = link;
        this.ingredients = ingredients;
    }

    public String getName() { return name; }
    public List<String> getSteps() { return steps; }
    public int getServings() { return servings; }
    public int getPreparation() { return preparation; }
    public int getDifficulty() { return difficulty; }
    public double getRating() { return rating; }
    public String getLink() { return link; }
    public List<Ingredient> getIngredients() { return ingredients; }

    @Override
    public String toString() {
        return "Recipe [difficulty=" + difficulty + ", ingredients=" + ingredients + ", link=" + link + ", name=" + name
                + ", preparation=" + preparation + ", rating=" + rating + ", servings=" + servings + ", steps=" + steps
                + "]";
    }

    

    
}