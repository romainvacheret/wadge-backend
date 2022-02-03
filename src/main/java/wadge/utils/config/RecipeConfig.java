package wadge.utils.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import wadge.dao.RecipeRepository;
import wadge.utils.db.SequenceGenerator;
import wadge.utils.scraping.ScrapingTask;
import wadge.utils.scraping.impl.TopSearchesScraper;
import wadge.utils.scraping.model.PageScraper;

@Configuration
@AllArgsConstructor
public class RecipeConfig {
    private final SequenceGenerator sequenceGenerator;
    // Can be changed to the user's convenience
    private static final int THREAD_COUNT = 1;

    @Bean
    CommandLineRunner recipeCommandLineRunner(final RecipeRepository repository) {

        return args -> {
            if(repository.findAll().isEmpty()) {
                Optional<PageScraper> scraper;
                try {
                    scraper = Optional.of(new TopSearchesScraper(TopSearchesScraper.TOP_SEARCH_URL));
                } catch(IOException e) {
                    scraper = Optional.empty();
                }

                final List<String> categories = scraper.isPresent() ? scraper.get().scrap() : List.of();
                final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

                categories.forEach(category -> executor.execute(new ScrapingTask(
                    repository,
                    category,
                    sequenceGenerator
                )));

                executor.shutdown();
            }
        };
    }
}
