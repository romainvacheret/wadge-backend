package wadge.utils.scraping.impl;

import wadge.model.recipe.external.MarmitonRecipe;
import wadge.utils.scraping.model.AbstractPageScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RecipeCategoryScraper extends AbstractPageScraper {
    public RecipeCategoryScraper(final String url) throws IOException {
        super(url);
    }

    @Override
    public List<MarmitonRecipe> scrap() {
        final int maxPageIndex = 5; // Can be changed according to the needs
        final List<MarmitonRecipe> recipeList = new ArrayList<>();

        try {
            IntStream.range(1, maxPageIndex + 1)
                .boxed()
                .forEach(index -> {
                    final RecipeListScraper scraper;
                    final String x = String.format(url + "&page=%d", index);
                    try {
                        scraper = new RecipeListScraper(x);
                        recipeList.addAll(scraper.scrap());
                    } catch (IOException e) {}
                });
        } catch(Exception e) {} // TODO check exception


        return recipeList;
    }

    public static void main(String[] args) throws IOException {
        final String url = "https://www.marmiton.org/recettes/recherche.aspx?aqt=gateau";
        final RecipeCategoryScraper scraper = new RecipeCategoryScraper(url);
        final List<MarmitonRecipe> recipeList = scraper.scrap();
        System.out.println(recipeList.size());
        System.out.println(recipeList);
    }
}

