package wadge.api;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import wadge.FakeRepository;
import wadge.model.recipe.Recipe;
import wadge.model.recipe.RecipeTag;
import wadge.service.recipe.impl.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class RecipeControllerTest {
    @MockBean
    private RecipeService recipeService;
    private RecipeController underTest;

    @BeforeEach
    void setUp() {
        underTest = new RecipeController(recipeService);
        when(recipeService.getAllRecipes()).thenReturn(FakeRepository.getRecipes());
    }

    @Test
    void getRecipesIngredient() {
        underTest.getRecipesIngredient(Recipe.builder().build());
        verify(recipeService).getRecipeIngredient(Recipe.builder().build());
    }

    @Test
    void getAllRecipes() {
        underTest.getAllRecipes();
        verify(recipeService).getAllRecipes();
    }

    @Test
    void getRecipesFromTag() {
        underTest.getRecipesFromTag(RecipeTag.FAVORITE);
        verify(recipeService).getTaggedRecipes(RecipeTag.FAVORITE);
    }

    @Test
    void addTagToRecipe() {
        final ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        final ArgumentCaptor<RecipeTag> tagCaptor = ArgumentCaptor.forClass(RecipeTag.class);

        underTest.addTagToRecipe(1L, RecipeTag.DONE);
        verify(recipeService).addTagToRecipe(longCaptor.capture(), tagCaptor.capture());
        assertEquals(1L, longCaptor.getValue());
        assertEquals(RecipeTag.DONE, tagCaptor.getValue());
    }

    @Test
    void removeTagFromRecipe() {
        final ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        final ArgumentCaptor<RecipeTag> tagCaptor = ArgumentCaptor.forClass(RecipeTag.class);

        underTest.removeTagToRecipe(1L, RecipeTag.DONE);
        verify(recipeService).removeTagToRecipe(longCaptor.capture(), tagCaptor.capture());
        assertEquals(1L, longCaptor.getValue());
        assertEquals(RecipeTag.DONE, tagCaptor.getValue());
    }

}