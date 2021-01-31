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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.model.fridge.FridgeFood;
import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService;
import wadge.service.fridge.FridgeService.RecallType;
import wadge.service.recipe.impl.RecipeService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RecipeController {   
    private final RecipeService recipeService;
    private final FridgeService fridgeService;
    

    @Autowired
    public RecipeController(RecipeService recipeService, FridgeService fridgeService) {
        this.recipeService = recipeService;
        this.fridgeService = fridgeService;
    }

    @RequestMapping(path="/recipes", method=RequestMethod.GET)
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @RequestMapping(path="/recipes/fridge", method=RequestMethod.GET)
    public List<Recipe> getRecipesUsingFridge() {
        Map<RecallType, List<String>> products = Arrays.asList(RecallType.values()).stream().map(type -> Map.entry(type, fridgeService.getExpirationList(type).stream().map(FridgeFood::getName)
            .collect(Collectors.toList()))).collect(Collectors.
            toMap(Map.Entry<RecallType, List<String>>::getKey, Map.Entry<RecallType, List<String>>::getValue));
        return recipeService.getRecipesUsingFridge(products);
    }

    @RequestMapping(path="/recipes/listfood", method=RequestMethod.POST)
    public List<Recipe> getRecipesUsingListFood( @RequestBody JsonNode listFood){
        ObjectMapper mapper = new ObjectMapper();
        List <String> list = Arrays.asList(mapper.convertValue(listFood, String[].class));
        System.out.println(list);
        
        List<Recipe> tmp = recipeService.getRecipesUsingUserList(list);
        System.out.println(tmp);
        return tmp;
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
