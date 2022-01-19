package wadge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;
import wadge.model.recipe.Recipe;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, Long> {}
