package wadge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import wadge.model.recipe.Recipe;

public interface RecipeRepository extends MongoRepository<Recipe, Long> {}
