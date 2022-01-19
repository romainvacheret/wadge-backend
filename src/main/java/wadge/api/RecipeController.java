package wadge.api;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import wadge.model.recipe.Recipe;
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

    @GetMapping(path="/recipes/favorites")
	public List<Recipe> getFavoriteList(){
    	return recipeService.getFavoritesRecipes();
    }
   
    @PostMapping(path="/recipes/addFavorite")
	public void addFavorite(@RequestBody final Recipe recipe){
    	   recipeService.addFavoriteRecipe(recipe);
    }

    @PostMapping(path = "/recipes/removeFavorite")
	public List<Recipe> removeFavorite(@RequestBody final Recipe recipe){
    	return recipeService.deleteFavoriteRecipe(recipe.getLink());
    }

	@GetMapping(path="/recipes/doneRecipes")
	public List<Recipe> getDoneRecipeList(){
		return recipeService.getDoneRecipes();
	}
	
	@PostMapping(path="/recipes/addtoDoneRecipe")
	public void addToDone(@RequestBody final Recipe recipe){
		recipeService.addDoneRecipe(recipe);
	}
} 
