package wadge.service.recipe.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.FakeRepository;
import wadge.dao.RecipeRepository;
import wadge.model.recipe.Recipe;
import wadge.model.recipe.RecipeTag;
import wadge.service.food.FoodService;
import wadge.service.fridge.FridgeService;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class RecipeServiceTest {
    @MockBean
    private RecipeRepository recipeRepository;
    @MockBean
    private FridgeService fridgeService;
    @MockBean
    private FoodService foodService;
    private RecipeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RecipeService(recipeRepository, fridgeService, foodService);
        when(recipeRepository.findAll()).thenReturn(FakeRepository.getRecipes());
    }
    
    @Test
    void getAllRecipes() {
        Assertions.assertFalse(underTest.getAllRecipes().isEmpty());
        verify(recipeRepository).findAll();
    }

    @Test
    @Ignore
    void getRecipesUsingFridge() {
        underTest.getRecipesUsingFridge();
    }


    @Test
    void getTaggedRecipe() {
        final List<Recipe> result = underTest.getTaggedRecipes(RecipeTag.FAVORITE);
        assertEquals(1, result.size());
        assertTrue(result.stream()
            .allMatch(recipe -> recipe.getTags().contains(RecipeTag.FAVORITE)));
    }

    /*
    @Test
    @Ignore
    void addTagToRecipe() {
        final Recipe recipe = underTest.getAllRecipes().get(1);
        assertTrue(recipe.getTags().isEmpty());
        underTest.addTagToRecipe(2, RecipeTag.DONE);
        assertTrue(underTest.getTaggedRecipes(RecipeTag.DONE).get(0).getTags().contains(RecipeTag.DONE));
    }

     */

    @Test
    void removeTagToRecipe() {
        assertTrue(underTest.getAllRecipes().get(0).getTags().contains(RecipeTag.FAVORITE));
        underTest.removeTagToRecipe(1, RecipeTag.FAVORITE);
        verify(recipeRepository).findById(1L);
    }

}
