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
                    final String x = String.format("%s&page=%d", url, index);
                    try {
                        scraper = new RecipeListScraper(x);
                        recipeList.addAll(scraper.scrap());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch(Exception e) {
            e.printStackTrace();
        }


        return recipeList;
    }
}

