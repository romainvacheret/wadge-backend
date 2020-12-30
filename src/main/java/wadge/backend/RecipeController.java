package wadge.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import wadge.recipe.api.IDataManager;
import wadge.recipe.impl.DataManager;
import wadge.recipe.impl.Recipe;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RecipeController {   
    private static final String RECIPES = "recipe_list.json";
    private static final List<Recipe> recipeList;
    private static final IDataManager manager = DataManager.getInstance();

    static {
        recipeList = new ArrayList<>();
        recipeList.addAll(manager.readFile(RECIPES));
    }

    @RequestMapping(path="/recipes", method= RequestMethod.GET)
    public List<Recipe> getRecipes() {
        return recipeList;
    }

}