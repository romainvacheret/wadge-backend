package wadge.utils.scraping.impl;

import wadge.model.recipe.external.MarmitonRecipe;
import wadge.utils.scraping.model.AbstractPageScraper;

import java.io.IOException;
import java.util.List;

public class TopSearchesScraper extends AbstractPageScraper {
    private static final String topSearchesXPath = "//*[@id=\"content\"]/div[3]/div[1]/div/div[2]";
    private static final String TOP_SEARCH_URL = "https://www.marmiton.org/recettes/top-internautes-recherche.aspx";

    protected TopSearchesScraper(final String url) throws IOException {
        super(url);
    }

    private List<String> scrapTopSearchesUrls() {
        return doc.selectXpath(topSearchesXPath)
                .first()
                .children()
                .stream()
                .map(element -> element.attr("href").replace("\"", ""))
                .toList();
    }

    @Override
    public List<MarmitonRecipe> scrap() {
        return (List<MarmitonRecipe>) scrapTopSearchesUrls().stream().skip(20)
            .map(url -> {
                try {
                    return new RecipeCategoryScraper(url).scrap();
                } catch (IOException e) {
                    e.printStackTrace();
                    return List.of();
                }
            })
            .flatMap(List::stream)
            .toList();

    }

    public static void main(String[] args) throws IOException {
        final TopSearchesScraper scraper = new TopSearchesScraper(TOP_SEARCH_URL);
        List<MarmitonRecipe> recipeList = scraper.scrap();
        System.out.println(recipeList);
        System.out.println(recipeList.getClass());
        System.out.println(recipeList.get(0).getClass());
        System.out.println(recipeList.size());
    }
}
