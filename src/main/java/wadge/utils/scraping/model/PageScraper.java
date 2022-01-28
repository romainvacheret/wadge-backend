package wadge.utils.scraping.model;

@FunctionalInterface
public interface PageScraper {
    <T> T scrap();
}
