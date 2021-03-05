package wadge.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService;
import wadge.service.recipe.impl.RecipeSelection.Parameter;
import wadge.service.recipe.impl.RecipeService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RecipeController {   
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService, FridgeService fridgeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(path = "/recipes/search")
	public List<Recipe> getRecipesFromMarmiton(@RequestBody JsonNode ingredients) {
		ObjectMapper mapper = new ObjectMapper();
		List<String> list = Arrays.asList(mapper.convertValue(ingredients, String[].class));
		String query = list.stream().collect(Collectors.joining("-"));
		return this.recipeService.getRecipesFromMarmiton(query);
	}

    @PostMapping(path = "/recipes/ingredient")
    public Map<String, String> getRecipesIngredient(@RequestBody Recipe recipes) {
        return recipeService.getRecipeIngredient(recipes);
    }

    @PostMapping(path="/recipes")
    public List<Recipe> getSelectedRecipes(@RequestBody Map<String, Parameter> node) {
        return recipeService.selectRecipes(node.get("selection")); 
    }
    @GetMapping(path="/recipes/favorites")
	public List<Recipe> getFavorieList(){
    	return recipeService.getFavoriesRecipes();
    }
   
    @PostMapping(path="/recipes/addFavorite")
	public void addFavorie(@RequestBody Recipe recipe){
    	   recipeService.addFavoriteRecipe(recipe);
    
    }
    @PostMapping(path = "/recipes/removeFavorite")
	public List<Recipe> removeFavorite(@RequestBody Recipe recipe){
    	return recipeService.deleteFavoriteRecipe(recipe.getLink());
    	
    }
} 
