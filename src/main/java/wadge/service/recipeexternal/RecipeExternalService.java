package wadge.service.recipeexternal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IRecipeExternalDao;
import wadge.model.recipe.Recipe;
import wadge.model.recipeExternal.RecipeExternal;


@Service
public class RecipeExternalService {
	private final IRecipeExternalDao recipeExternalDao;
	
	@Autowired
	public RecipeExternalService(@Qualifier("jsonRecipeExtDao") IRecipeExternalDao recipeExternalDao) {
		this.recipeExternalDao = recipeExternalDao;
	}

	public List<Recipe> writeRecipe(String searchquery){
		List<RecipeExternal> x = this.recipeExternalDao.recipeExternalsFromUrl(searchquery);
		System.out.println(recipeExternalDao.toRecipe(x));
		return recipeExternalDao.toRecipe(x);
		
	}
}
