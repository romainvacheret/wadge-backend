package wadge.recipe;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Recipe {
    private final String name;
    private final List<String> steps;
    private final Object quantity;
    private final List<Map<String, Object>> ingredients;

    public Recipe(String name, List<String> steps, Object quantity, List<Map<String, Object>> ingredients) {
        this.name = name;
        this.steps = steps;
        this.quantity = quantity;
        this.ingredients = ingredients;
    }

    public static List<Map<String, Object>> readRecipes() {
        JSONParser jsonP = new JSONParser();
        List<Map<String, Object>> list = new ArrayList<>();
        // Add builder to have cleaner code
        // List<RecipeBuilder> builders = new ArrayList<>();
        
        try{
            Object jsonList = jsonP.parse(new FileReader("recipe_list.json"));
            JSONArray recipes = (JSONArray) jsonList;
            Iterator<JSONObject> iterator = recipes.iterator();
            while (iterator.hasNext()) {
                JSONObject recipe = iterator.next();
                list.add(Map.of(
                    "nom",recipe.get("nom"),
                    "difficulte",recipe.get("difficulte"),
                    "personnes",recipe.get("personnes"),
                    "etapes",(((JSONArray) recipe.get("etapes")).toArray()),
                    "ingredients",(((JSONArray) recipe.get("ingredients")).toArray())
                ));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
