package wadge.api;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wadge.service.recipeexternal.RecipeExternalService;

import java.util.Arrays;
import java.util.List;


@CrossOrigin(origins ="https://localhost:3000")
@RestController
public class RecipeExternalControler {
	RecipeExternalService recipeExternalService;
	@Autowired
	RecipeExternalControler(RecipeExternalService recipeExternalService){this.recipeExternalService=recipeExternalService;}
	
	@PostMapping(path = "/searchRecipe/{queryingredient}")
	public void getRecipeExternal(@RequestBody JsonNode queryingredients) {
		ObjectMapper mapper = new ObjectMapper();
		List<String> list = Arrays.asList(mapper.convertValue(queryingredients, String[].class));
		String query = list.stream().
				reduce((s1, s2) -> new StringBuffer(s1).append("-").append(s2).toString()).get();
		this.recipeExternalService.writeRecipe(query);
	}
}
