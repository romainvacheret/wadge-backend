package wadge.utils.scraping;

import wadge.dao.RecipeRepository;
import wadge.model.recipe.external.MarmitonRecipe;
import wadge.utils.db.SequenceGenerator;
import wadge.utils.scraping.impl.RecipeCategoryScraper;

import java.io.IOException;
import java.util.Optional;

public record ScrapingTask(RecipeRepository repository,
       String category, SequenceGenerator sequenceGenerator) implements Runnable {

    private Optional<RecipeCategoryScraper> getScraper() {
        try {
            return Optional.of(new RecipeCategoryScraper(category));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void run() {
        getScraper().ifPresent(scraper ->
            repository.saveAll(scraper.scrap().stream()
                .map(MarmitonRecipe::toRecipe)
                .peek(recipe -> recipe.setId(
                    sequenceGenerator.generateSequence("recipe_sequence")))
                .filter(recipe -> !recipe.getName().equals("")) // Sponsored recipes
                .toList())
        );

    }
}
