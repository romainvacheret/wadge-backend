package wadge.api;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService;
import wadge.service.recipe.impl.RecipeService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RecipeController {   
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService, FridgeService fridgeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(path="/recipes", method=RequestMethod.GET)
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @RequestMapping(path="/recipes/fridge", method=RequestMethod.GET)
    public List<Recipe> getRecipesUsingFridge() {
        return recipeService.getRecipesUsingFridge();
    }

    @RequestMapping(path="/recipes/listfood", method=RequestMethod.POST)
    public List<Recipe> getRecipesUsingListFood( @RequestBody JsonNode listFood){
        ObjectMapper mapper = new ObjectMapper();
        List <String> list = Arrays.asList(mapper.convertValue(listFood, String[].class));
        return recipeService.getRecipesUsingUserList(list);
    }

    @PostMapping(path = "/recipes/search")
	public List<Recipe> getRecipeExternal(@RequestBody JsonNode queryingredients) {
		ObjectMapper mapper = new ObjectMapper();
		List<String> list = Arrays.asList(mapper.convertValue(queryingredients, String[].class));
		String query = list.stream().
				reduce((s1, s2) -> new StringBuffer(s1).append("-").append(s2).toString()).get();
		return this.recipeService.writeRecipe(query);
	}

}
