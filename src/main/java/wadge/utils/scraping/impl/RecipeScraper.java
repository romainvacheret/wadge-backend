package wadge.utils.scraping.impl;

import wadge.model.recipe.Ingredient;
import wadge.model.recipe.external.MarmitonRecipe;
import wadge.utils.scraping.model.AbstractPageScraper;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class RecipeScraper extends AbstractPageScraper {
    private static final String PREPARATION_XPATH = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[5]/div[1]/span/p";
    private static final String NAME_XPATH = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[1]/h1";
    private static final String DIFFICULTY_XPATH = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[5]/div[2]/span/p";
    private static final String RATING_XPATH = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span";
    private static final String SERVINGS_XPATH = "//*[@id=\"simple-tabpanel-0\"]/div[1]/div[1]/div/span[1]";
    private static final String OPINION_XPATH = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[2]/div[2]/a/span";
    private static final String INGREDIENT_LIST_XPATH = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]";
    private static final String STEP_LIST_XPATH = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/ul";
    private static final String INGREDIENT_QUANTITY_XPATH = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/span/span/div[2]/div/span";
    private static final String INGREDIENT_QUANTITY_XPATH_SECOND_CASE = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/span/a/div[2]/div/span";
    private static final String INGREDIENT_QUANTITY_XPATH_THIRD_CASE = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/div[2]/div/span";
    private static final String INGREDIENT_NAME_XPATH = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/span/span/div[2]/span/span[2]";
    private static final String INGREDIENT_NAME_XPATH_SECOND_CASE = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/span/a/div[2]/span/span[2]";
    private static final String INGREDIENT_NAME_XPATH_THIRD_CASE = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/div[2]/span/span[2]";

    public RecipeScraper(final String url) throws IOException {
        super(url);
    }

    private String getSubString(final String string, final String regex, final int index) {
        try {
            return string.split(regex)[index];
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public MarmitonRecipe scrap() {
        final String name = getValueFromXPath(NAME_XPATH);
        final String preparation = getValueFromXPath(PREPARATION_XPATH).replace("&nbsp", " ");
        final String difficulty = getValueFromXPath(DIFFICULTY_XPATH);
        final String fullRating = getValueFromXPath(RATING_XPATH);
        final String rating = getSubString(fullRating, "/", 0);
        final String ratingFract = getSubString(fullRating, "/", 1);
        final String servings = getValueFromXPath(SERVINGS_XPATH);
        final String opinion = getValueFromXPath(OPINION_XPATH).split(" ")[0];

        final int ingredientsCount = doc.selectXpath(INGREDIENT_LIST_XPATH).get(0).childrenSize();
        final int stepsCount = doc.selectXpath(STEP_LIST_XPATH).get(0).childrenSize();
        final List<Ingredient> ingredients = scrapIngredients(ingredientsCount);
        final List<String> steps = scrapSteps(stepsCount);

        return MarmitonRecipe.builder()
            .name(name)
            .preparation(preparation)
            .difficulty(difficulty)
            .rating(rating)
            .ratingfract(ratingFract)
            .servings(servings)
            .opinion(opinion)
            .link(url)
            .ingredients(ingredients)
            .steps(steps)
            .build();
        }

    protected List<String> scrapSteps(final int stepsCount) {
        final String stepXpath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/ul/li[%d]/p";
        return IntStream.range(1 , stepsCount + 1)
            .boxed()
            .map(idx -> getValueFromXPath(getCompletedXPath(stepXpath, idx)))
            .toList();
    }

    protected Ingredient scrapIngredient(final int ingredientIndex) {

        String name = getValueFromXPath(getCompletedXPath(INGREDIENT_NAME_XPATH, ingredientIndex));
        String quantity = getValueFromXPath(getCompletedXPath(INGREDIENT_QUANTITY_XPATH, ingredientIndex));

        if(name.equals("")) {
             name = getValueFromXPath(getCompletedXPath(INGREDIENT_NAME_XPATH_SECOND_CASE, ingredientIndex));
             quantity = getValueFromXPath(getCompletedXPath(INGREDIENT_QUANTITY_XPATH_SECOND_CASE, ingredientIndex));
        }
        if(name.equals("")) {
            name = getValueFromXPath(getCompletedXPath(INGREDIENT_NAME_XPATH_THIRD_CASE, ingredientIndex));
            quantity = getValueFromXPath(getCompletedXPath(INGREDIENT_QUANTITY_XPATH_THIRD_CASE, ingredientIndex));
        }

        return Ingredient.builder()
            .name(name)
            .quantity(quantity)
            .build();
    }

    protected List<Ingredient> scrapIngredients(final int ingredientsCount) {
        return IntStream.range(1 , ingredientsCount + 1)
            .boxed()
            .map(this::scrapIngredient)
            .toList();
    }
}