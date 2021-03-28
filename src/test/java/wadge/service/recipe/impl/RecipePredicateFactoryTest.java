package wadge.service.recipe.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.service.recipe.impl.RecipeSelection.Parameter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipePredicateFactoryTest {
    @Test
    public void getComparatorTest() {
        List.of(Parameter.values()).stream().forEach(
            param -> assertTrue(RecipePredicateFactory.getPredicate(param, 0) instanceof Predicate<?>));
    }
}
