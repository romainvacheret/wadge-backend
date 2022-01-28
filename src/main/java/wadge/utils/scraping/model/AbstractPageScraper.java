package wadge.utils.scraping.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class AbstractPageScraper implements PageScraper {
    protected final String url;
    protected final Document doc;

    protected AbstractPageScraper(final String url) throws IOException {
        System.out.println(url);
        this.url = url;
        doc = Jsoup.connect(url).get();
    }

    protected String getCompletedXPath(final String xPath, final int idx) {
        return String.format(xPath, idx);
    }
    protected String getValueFromXPath(final String xPath) {
        try {
            return doc.selectXpath(xPath).get(0).text();
        } catch(IndexOutOfBoundsException e) {
            return "";
        }
    }
}
