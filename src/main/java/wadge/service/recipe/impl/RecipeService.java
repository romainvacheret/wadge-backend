package wadge.service.recipe.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import wadge.dao.api.IRecipeDao;
import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService;
import wadge.service.fridge.FridgeService.RecallType;
import wadge.service.recipe.api.AbstractRecipeSelection;

@Service
public class RecipeService {
    private final IRecipeDao recipeDao;
    private final FridgeService fridgeService;

    @Autowired
    public RecipeService(@Qualifier("jsonRecipeDao") IRecipeDao recipeDao, FridgeService fridgeService) {
        this.recipeDao = recipeDao;
        this.fridgeService = fridgeService;
    }

    public List<Recipe> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    IntPredicate toto = x -> x > 0;

    ToIntFunction<Recipe> recipeScoring = recipe -> recipe.getIngredients().stream().map(SelectionWithFridge.ingredientScoring)
            .filter(Optional::isPresent).map(Optional::get).reduce(0, (a, b) -> a + b);

    public List<Recipe> getRecipesUsingFridge(Map<RecallType, List<String>> products) {
        FridgeSelection select = new FridgeSelection(getAllRecipes(), products);
        System.out.println(fridgeService.getAllFridge());
        // return select.select().sort();

        AbstractRecipeSelection sel = new SelectionWithFridge(getAllRecipes().stream().collect(Collectors.toSet()), fridgeService);
        return sel.compute(recipeScoring).filter(toto).sort(Collections.reverseOrder(Map.Entry.comparingByKey()));
    }

    public List<Recipe> getRecipesUsingUserList(List<String> userSelection){
        UserListSelection userSelect = new UserListSelection(getAllRecipes(), userSelection);
        return userSelect.select().sort();
    }
}
