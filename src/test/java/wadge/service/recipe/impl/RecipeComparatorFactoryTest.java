package wadge.service.recipe.impl;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.service.recipe.impl.RecipeSelection.Parameter;

@RunWith(SpringRunner.class)
@SpringBootTest
    
public class RecipeComparatorFactoryTest {

    @Test
    public void getComparatorTest() {
        List.of(Parameter.values()).stream().forEach(
            param -> assertTrue(RecipeComparatorFactory.getComparator(param) instanceof Comparator<?>));
    }
    
}
