package wadge.utils.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import wadge.dao.RecipeRepository;
import wadge.model.recipe.external.MarmitonRecipe;
import wadge.utils.db.SequenceGenerator;
import wadge.utils.scraping.impl.TopSearchesScraper;
import wadge.utils.scraping.model.PageScraper;

@Configuration
@AllArgsConstructor
public class RecipeConfig {
    private final SequenceGenerator sequenceGenerator;
    
    @Bean
    @SuppressWarnings("unchecked")
    CommandLineRunner recipeCommandLineRunner(final RecipeRepository repository) {
        return args -> {
            if(repository.findAll().isEmpty()) {
				Optional<PageScraper> scraper;
				try {
					scraper = Optional.of(new TopSearchesScraper(TopSearchesScraper.TOP_SEARCH_URL));
				} catch(IOException e) {
					scraper = Optional.empty();
				}

                 scraper.ifPresent(s -> repository.saveAll(
                     ((List< MarmitonRecipe>) s.scrap()).stream()
                    .map(MarmitonRecipe::toRecipe)
                    .peek(recipe -> recipe.setId(
                        sequenceGenerator.generateSequence("recipe_sequence")))
                    .toList()
                ));
            }
        };
    }
}
