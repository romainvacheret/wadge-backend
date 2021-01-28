package wadge.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wadge.model.recipeExternal.RecipeExternal;
import wadge.service.recipeExternal.RecipeExternalService;
import java.util.List;


@CrossOrigin(origins ="https://localhost:3000")
@RestController
public class RecipeExternalControler {
	RecipeExternalService recipeExternalService;
	@Autowired
	RecipeExternalControler(RecipeExternalService recipeExternalService){this.recipeExternalService=recipeExternalService;}
	
	@PostMapping(path = "/searchRecipe/{queryingredient}")
	public void getRecipeExternal(@PathVariable String queryingredients) {
		this.recipeExternalService.writeRecipe(queryingredients);
	}
}
