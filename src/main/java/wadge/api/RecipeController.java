package wadge.api;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import wadge.model.recipe.Recipe;
import wadge.model.recipe.RecipeTag;
import wadge.service.recipe.impl.RecipeSelection.Parameter;
import wadge.service.recipe.impl.RecipeService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class RecipeController {   
    private final RecipeService recipeService;

    // TODO refactor -> rename + change map types
    @PostMapping(path = "/recipes/ingredient")
    public Map<String, String> getRecipesIngredient(@RequestBody final Recipe recipes) {
        return recipeService.getRecipeIngredient(recipes);
    }

    // TODO refactor -> change map types
    @PostMapping(path="/recipes")
    public List<Recipe> getSelectedRecipes(@RequestBody final Map<String, Parameter> node) {
        return recipeService.selectRecipes(node.get("selection")); 
    }

    @GetMapping(path="/recipes")
    public  List<Recipe> getAllRecipes(){
    	return recipeService.getAllRecipes();
    }

    // TODO refactor -> change logic for "special" recipes

    // ---- Tagged Recipes ----

    @GetMapping(path="/recipes/tagged/{tag}")
    public List<Recipe> getRecipesFromTag(@PathVariable final RecipeTag tag) {
        return recipeService.getTaggedRecipes(tag);
    }

    @PostMapping(path="/recipes/{id}/tagged/{tag}")
    public void addTagToRecipe(@PathVariable final long id, @PathVariable final RecipeTag tag) {
        recipeService.addTagToRecipe(id, tag);
    }

    @DeleteMapping(path="/recipes/{id}/tagged/{tag}")
    public void removeTagToRecipe(@PathVariable final long id, @PathVariable final RecipeTag tag) {
        recipeService.removeTagToRecipe(id, tag);
    }
}
