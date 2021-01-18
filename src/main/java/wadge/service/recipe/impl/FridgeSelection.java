package wadge.service.recipe.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService.RecallType;
import wadge.service.recipe.api.RecipeSelection;

public class FridgeSelection implements RecipeSelection {
    private List<Recipe> recipes;
    private List<Map.Entry<Integer, Recipe>> scores;
    Map<String, Integer> scoringMap = new HashMap<>();

    public FridgeSelection(List<Recipe> recipes, Map<RecallType, List<String>> products) {
        this.recipes = recipes;
        defineScoringMap(products);
    }

    void addToSet(Set<String> mySet, String key, Integer value) {
        if(!mySet.contains(key)) {
            mySet.add(key);
            scoringMap.put(key, value);
        }
    }

    void defineScoringMap(Map<RecallType, List<String>> products) {
        Set<String> nameSet = new HashSet<>();
        try {
            products.get(RecallType.TWO_DAYS).stream().forEach(product -> addToSet(nameSet, product, 4));
            products.get(RecallType.FIVE_DAYS).stream().forEach(product -> addToSet(nameSet, product, 3));
            products.get(RecallType.SEVEN_DAYS).stream().forEach(product -> addToSet(nameSet, product, 2));
            products.get(RecallType.FORTEEN_DAYS).stream().forEach(product -> addToSet(nameSet, product, 1));
            products.get(RecallType.OTHER).stream().forEach(product -> addToSet(nameSet, product, 1));
        } catch(NullPointerException e) {
            // No food for the given RecallType 
        }
    }

    Function<Ingredient, Optional<Integer>> ingredientScoring = ingredient -> Optional
            .ofNullable(scoringMap.get(ingredient.getName()));

    ToIntFunction<List<Ingredient>> recipeScoring = ingredients -> ingredients.stream()
            .map(ingredientScoring).filter(Optional::isPresent).map(Optional::get).reduce(0, (a, b) -> a + b);

    Function<Recipe, Map.Entry<Integer, Recipe>> recipeToEntry = recipe -> Map
            .entry(recipeScoring.applyAsInt(recipe.getIngredients()), recipe);

    @Override
    public RecipeSelection select() {
        scores = recipes.stream().map(recipeToEntry)
                .collect(Collectors.toList());
        return this;
    }

    @Override
    public List<Recipe> sort() {
        return scores.stream().filter(score -> score.getKey() != 0).sorted(Collections.
            reverseOrder(Map.Entry.comparingByKey())).
            map(Map.Entry<Integer, Recipe>::getValue).collect(Collectors.toList());
    }
}
