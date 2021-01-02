package wadge.recipe.api;

import java.util.List;

import wadge.recipe.impl.Recipe;

public interface IDataManager {
    List<Recipe> readFile(String fileName);
}
