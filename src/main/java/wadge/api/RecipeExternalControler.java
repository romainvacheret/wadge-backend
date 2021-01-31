package wadge.api;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wadge.model.recipe.Recipe;
import wadge.service.recipeexternal.RecipeExternalService;


@CrossOrigin(origins ="https://localhost:3000")
@RestController
public class RecipeExternalControler {
	RecipeExternalService recipeExternalService;
	@Autowired
	RecipeExternalControler(RecipeExternalService recipeExternalService){this.recipeExternalService=recipeExternalService;}
	
	@PostMapping(path = "/recipes/search")
	public List<Recipe> getRecipeExternal(@RequestBody JsonNode queryingredients) {
		ObjectMapper mapper = new ObjectMapper();
		List<String> list = Arrays.asList(mapper.convertValue(queryingredients, String[].class));
		String query = list.stream().
				reduce((s1, s2) -> new StringBuffer(s1).append("-").append(s2).toString()).get();
		return this.recipeExternalService.writeRecipe(query);
	}
}
