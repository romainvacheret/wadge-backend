package wadge.api;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import wadge.model.recipe.Recipe;
import wadge.service.recipe.impl.RecipeService;

import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;
    private RecipeController underTest;

    @BeforeEach
    void setUp() {
        underTest = new RecipeController(recipeService);
    }

    @Test
    void getRecipesIngredient() {
        underTest.getRecipesIngredient(Recipe.builder().build());
        verify(recipeService).getRecipeIngredient(Recipe.builder().build());
    }

    @Test
    @Ignore
    void getSelectedRecipes() {
    }

    @Test
    void getAllRecipes() {
        underTest.getAllRecipes();
        verify(recipeService).getAllRecipes();
    }

    @Test
    @Ignore
    void getFavoriteList() {
    }

    @Test
    @Ignore
    void addFavorite() {
    }

    @Test
    @Ignore
    void removeFavorite() {
    }

    @Test
    @Ignore
    void getDoneRecipeList() {
    }

    @Test
    @Ignore
    void addToDone() {
    }
}