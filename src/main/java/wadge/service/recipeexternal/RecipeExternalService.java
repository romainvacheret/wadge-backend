package wadge.service.recipeexternal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import wadge.dao.api.IRecipeExternalDao;

/*import wadge.model.recipeExternal.RecipeExternal;
import java.util.List;*/

@Service
public class RecipeExternalService {
	private final IRecipeExternalDao recipeExternalDao;
	
	@Autowired
	public RecipeExternalService(@Qualifier("jsonRecipeExtDao") IRecipeExternalDao recipeExternalDao) {
		this.recipeExternalDao = recipeExternalDao;
	}
	/*public List<RecipeExternal> getAllExternalRecipe() {
		return recipeExternalDao.readExternalRecipe();
	}*/
	
	public void writeRecipe(String searchquery){
		this.recipeExternalDao.recipeExternalsFromUrl(searchquery);
		
	}
}
