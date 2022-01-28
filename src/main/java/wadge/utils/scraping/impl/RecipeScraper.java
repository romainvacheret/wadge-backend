package wadge.utils.scraping.impl;

import wadge.model.recipe.Ingredient;
import wadge.model.recipe.external.MarmitonRecipe;
import wadge.utils.scraping.model.AbstractPageScraper;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class RecipeScraper extends AbstractPageScraper {
    private static final String preparationXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[5]/div[1]/span/p";
    private static final String nameXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[1]/h1";
    private static final String difficultyXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[5]/div[2]/span/p";
    private static final String ratingXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span";
    private static final String servingsXPath = "//*[@id=\"simple-tabpanel-0\"]/div[1]/div[1]/div/span[1]";
    private static final String opinionXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[2]/div[2]/a/span";
    private static final String ingredientListXPath = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]";
    private static final String stepListXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/ul";
    private static final String ingredientQuantityXpath = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/span/span/div[2]/div/span";
    private static final String ingredientNameXPath = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/span/span/div[2]/span/span[2]";

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
        final String name = getValueFromXPath(nameXPath);
        final String preparation = getValueFromXPath(preparationXPath).replace("&nbsp", " ");
        final String difficulty = getValueFromXPath(difficultyXPath);
        final String fullRating = getValueFromXPath(ratingXPath);
        final String rating = getSubString(fullRating, "/", 0);
        final String ratingFract = getSubString(fullRating, "/", 1);
        final String servings = getValueFromXPath(servingsXPath);
        final String opinion = getValueFromXPath(opinionXPath).split(" ")[0];

        final int ingredientsCount = doc.selectXpath(ingredientListXPath).get(0).childrenSize();
        final int stepsCount = doc.selectXpath(stepListXPath).get(0).childrenSize();
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

        final String name = getValueFromXPath(getCompletedXPath(ingredientNameXPath, ingredientIndex));
        final String quantity = getValueFromXPath(getCompletedXPath(ingredientQuantityXpath, ingredientIndex));

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

    public static void main(String[] args) throws IOException {
        final String URL = "https://www.marmiton.org/recettes/recette_gateau-banane-de-steph_31190.aspx";
        final RecipeScraper scraper = new RecipeScraper(URL);
        System.out.println(scraper.scrap());
    }
}
