package wadge.api;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(path="/recipes")
    public List<Recipe> getSelectedRecipes(@RequestBody Map<String, Parameter> node) {
        return recipeService.selectRecipes(node.get("selection")); 
    }
} 