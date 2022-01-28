package wadge.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.external.MarmitonRecipe;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class MarmitonScraper {
    private final String url;
    private final Document doc;

    public MarmitonScraper(final String url) throws IOException {
        this.url = url;
        doc = Jsoup.connect(url).get();
    }

    public String getValueFromXPath(final String xPath) {
        try {
            return doc.selectXpath(xPath).get(0).text();
        } catch(IndexOutOfBoundsException e) {
            return "";
        }
    }

    private String getCompletedXPath(final String xPath, final int idx) {
        return String.format(xPath, idx);
    }

    private Ingredient scrapIngredient(final int ingredientIndex) {
        final String quantityXpath = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/span/span/div[2]/div/span";
        final String nameXPath = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]/div[%d]/div/span/span/div[2]/span/span[2]";

        final String name = getValueFromXPath(getCompletedXPath(nameXPath, ingredientIndex));
        final String quantity = getValueFromXPath(getCompletedXPath(quantityXpath, ingredientIndex));

        return Ingredient.builder()
            .name(name)
            .quantity(quantity)
            .build();
    }

    private List<Ingredient> scrapIngredients(final int ingredientsCount) {
        return IntStream.range(1 , ingredientsCount + 1)
            .boxed()
            .map(this::scrapIngredient)
            .toList();
    }

    private List<String> scrapSteps(final int stepsCount) {
        final String stepXpath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/ul/li[%d]/p";
        return IntStream.range(1 , stepsCount + 1)
                .boxed()
                .map(idx -> getValueFromXPath(getCompletedXPath(stepXpath, idx)))
                .toList();
    }

    public MarmitonRecipe scrapRecipe() {
        final String preparationXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[5]/div[1]/span/p";
        final String nameXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[1]/h1";
        final String difficultyXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[5]/div[2]/span/p";
        final String ratingXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span";
        final String servingsXPath = "//*[@id=\"simple-tabpanel-0\"]/div[1]/div[1]/div/span[1]";
        final String opinionXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/div[2]/div[2]/div[2]/a/span";
        final String ingredientListXPath = "//*[@id=\"simple-tabpanel-0\"]/div[2]/div/div/div/div[2]";
        final String stepListXPath = "//*[@id=\"__next\"]/div[3]/main/div/div/div[1]/div[1]/ul";

        final String name = getValueFromXPath(nameXPath);
        final String preparation = getValueFromXPath(preparationXPath).replace("&nbsp", " ");
        final String difficulty = getValueFromXPath(difficultyXPath);
        final String fullRating = getValueFromXPath(ratingXPath);
        final String rating = fullRating.split("/")[0]; // TODO assert not IndexOutOfBoundException
        final String ratingFract = "/" + fullRating.split("/")[1];
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

    public static void main(String[] args) throws IOException {
        final String URL = "https://www.marmiton.org/recettes/recette_gateau-banane-de-steph_31190.aspx";
        final MarmitonScraper scraper = new MarmitonScraper(URL);
        System.out.println(scraper.scrapRecipe());
    }
}
