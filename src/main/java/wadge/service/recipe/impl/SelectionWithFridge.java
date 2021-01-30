package wadge.service.recipe.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import wadge.model.fridge.FridgeFood;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.service.fridge.FridgeService;
import wadge.service.fridge.FridgeService.RecallType;
import wadge.service.recipe.api.AbstractRecipeSelection;

public class SelectionWithFridge extends AbstractRecipeSelection {
    static Map<String, Integer> scoringMap = new HashMap<>();

    public SelectionWithFridge(Set<Recipe> recipes, FridgeService fridgeService) {
        this.recipes = recipes;
        
        scoringMap.clear();
        defineScoringMap(Arrays.asList(RecallType.values()).stream().map((RecallType type) -> 
            Map.entry(type, fridgeService.getExpirationList(type))
            ).map(entry -> Map.entry(entry.getKey(), entry.getValue().stream().map(FridgeFood::getName).collect(Collectors.toList())))
            .collect(Collectors.toMap(Map.Entry<RecallType, List<String>>::getKey, 
            Map.Entry<RecallType, List<String>>::getValue)));
    }

    void addToSet(Set<String> mySet, String key, Integer value) {
        if (!mySet.contains(key)) {
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
            products.get(RecallType.FOURTEEN_DAYS).stream().forEach(product -> addToSet(nameSet, product, 1));
            products.get(RecallType.OTHER).stream().forEach(product -> addToSet(nameSet, product, 1));
        } catch (NullPointerException e) {
            // No food for the given RecallType
        }
    }

    public static final Function<Ingredient, Optional<Integer>> ingredientScoring = ingredient -> Optional
            .ofNullable(scoringMap.get(ingredient.getName()));

    ToIntFunction<List<Ingredient>> recipeScoring = ingredients -> ingredients.stream().map(ingredientScoring)
            .filter(Optional::isPresent).map(Optional::get).reduce(0, (a, b) -> a + b);

    BiFunction<Recipe, ToIntFunction<Recipe>, Map.Entry<Integer, Recipe>> recipeToEntry = (recipe, func) -> 
        Map.entry(func.applyAsInt(recipe), recipe);

    @Override   
    public AbstractRecipeSelection compute(ToIntFunction<Recipe> func) {
        
        scores = recipes.stream().map(recipe -> recipeToEntry.apply(recipe, func)).collect(Collectors.toList());
        return this;
    }

    @Override
    public AbstractRecipeSelection filter(IntPredicate predicate) {
        scores = scores.stream().filter(score -> predicate.test(score.getKey())).collect(Collectors.toList());
        return this;
    }

    @Override
    public List<Recipe> sort(Comparator<Map.Entry<Integer, Recipe>> comparator) {
        return scores.stream().sorted(comparator).map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
