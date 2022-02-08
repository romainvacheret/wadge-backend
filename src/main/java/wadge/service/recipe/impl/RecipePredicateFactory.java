package wadge.service.recipe.impl;

import java.util.function.Predicate;

import wadge.model.recipe.Recipe;
import wadge.service.recipe.impl.RecipeSelection.Parameter;


public class RecipePredicateFactory {
    private RecipePredicateFactory() {}
    
    public static Predicate<Recipe> getPredicate(final Parameter parameter, double minimum) {
        if(minimum < 0) {
            minimum = 0;
        }

        Predicate<Recipe> rtr;

        switch(parameter) {
            case BY_DIFFICULTY:
                if(minimum > 4) {
                    minimum = 4;
                } 

                final double tmp = minimum;
                rtr = x -> x.getDifficulty() >= tmp;
                break;

            case BY_RATING:
                if(minimum > 5) {
                    minimum = 5;
                }

                final double tmp2 = minimum;
                rtr = x -> x.getRating() >= tmp2;
                break;

            default:
               rtr = x -> true; 
        }

        return rtr;
    } 
}
