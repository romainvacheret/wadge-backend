package wadge.utils.scraping.impl;

import wadge.utils.scraping.model.AbstractPageScraper;

import java.io.IOException;
import java.util.List;

public class TopSearchesScraper extends AbstractPageScraper {
    private static final String topSearchesXPath = "//*[@id=\"content\"]/div[3]/div[1]/div/div[2]";
    public static final String TOP_SEARCH_URL = "https://www.marmiton.org/recettes/top-internautes-recherche.aspx";

    public TopSearchesScraper(final String url) throws IOException {
        super(url);
    }

    @Override
    public List<String> scrap() {
        return doc.selectXpath(topSearchesXPath)
            .first()
            .children()
            .stream()
            .map(element -> element.attr("href").replace("\"", ""))
            .toList();
    }
}
