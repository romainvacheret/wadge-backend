package wadge.recipe.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RecipeTest {
    private Recipe recipe;

    @Before
    public void setUp() {
        recipe = new Recipe(
            "Name", 
            List.of("Step 1", "Step 2", "Step 3"), 
            4, 
            45, 
            2, 
            "myLink.com", 
            List.of(new Ingredient("Ingredient", "12"))
        );
        System.out.println(recipe);
    }

    @Test
    public void getNameTest() {
        assertTrue(recipe.getName() instanceof String);
        assertEquals("Name", recipe.getName());
    }

    @Test
    public void getStepsTest() {
        assertTrue(recipe.getSteps() instanceof List<?>);
        List<String> steps = recipe.getSteps();
        assertTrue(steps.get(0) instanceof String);
        assertEquals(List.of("Step 1", "Step 2", "Step 3"), steps);
    }

    @Test
    public void getServingsTest() {
        assertTrue(Integer.valueOf(recipe.getServings()) instanceof Integer);
        assertEquals(4, recipe.getServings());
    }

    @Test
    public void getPreparationTest() {
        assertTrue(Integer.valueOf(recipe.getPreparation()) instanceof Integer);
        assertEquals(45, recipe.getPreparation());
    }

    @Test
    public void getDifficultyTest() {
        assertTrue(Integer.valueOf(recipe.getDifficulty()) instanceof Integer);
        assertEquals(2, recipe.getDifficulty());
    }

    @Test
    public void getLinkTest() {
        assertTrue(recipe.getLink() instanceof String);
        assertEquals("myLink.com", recipe.getLink());
    }

    @Test
    public void getIngredientsTest() {
        assertTrue(recipe.getIngredients() instanceof List<?>);
        assertTrue(recipe.getIngredients().get(0) instanceof Ingredient);
        assertEquals(List.of(new Ingredient("Ingredient", "12")), recipe.getIngredients());
    }

    @Test
    public void toStringTest() {
        String result = "Recipe [difficulty=2, ingredients=[Ingredient [name=Ingredient, quantity=12]], link=myLink.com, name=Name, preparation=45, servings=4, steps=[Step 1, Step 2, Step 3]]";
        assertEquals(result, recipe.toString());
    }

}
