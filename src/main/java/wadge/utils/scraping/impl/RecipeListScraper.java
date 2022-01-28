package wadge.utils.scraping.impl;

import wadge.model.recipe.external.MarmitonRecipe;
import wadge.utils.scraping.model.AbstractPageScraper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class RecipeListScraper extends AbstractPageScraper {
    private static final String recipeListXPath = "//*[@id=\"__next\"]/div[3]/main/div/div[2]/div[1]/div[1]/div[3]";
    private static final String urlXPath = "//*[@id=\"__next\"]/div[3]/main/div/div[2]/div[1]/div[1]/div[3]/a[%d]";
    private static final String marmitonUrl = "\"https://www.marmiton.org%s\"";

    public RecipeListScraper(final String url) throws IOException {
        super(url);
    }

    private Optional<MarmitonRecipe> scrapRecipe(final String url) {
        try {
            return Optional.ofNullable(new RecipeScraper(url).scrap());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private List<String> scrapRecipesLink() {
        final int recipeCount = doc.selectXpath(recipeListXPath).get(0).childrenSize();
        return IntStream.range(1, recipeCount + 1)
            .boxed()
            .map(idx -> getCompletedXPath(urlXPath, idx))
            .map(doc::selectXpath)
            .map(element -> String.format(
                marmitonUrl,
                element.first().attr("href")).replace("\"", ""))
            .toList();
    }

    @Override
    public List<MarmitonRecipe> scrap() {
        return scrapRecipesLink().stream()
            .map(this::scrapRecipe)
            .flatMap(Optional::stream)
            .toList();
    }

    public static void main(String[] args) throws IOException {
        final RecipeListScraper scraper = new RecipeListScraper("https://www.marmiton.org/recettes/recherche.aspx?aqt=gateau&page=6");
        System.out.println(scraper.scrap());
    }
}
