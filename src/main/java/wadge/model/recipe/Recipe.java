package wadge.model.recipe;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private String name;
    private List<String> steps;
    private int servings;
    private int preparation;
    private int difficulty;
    @EqualsAndHashCode.Exclude
    private double rating;
    @EqualsAndHashCode.Exclude
    private String link;
    private List<Ingredient> ingredients;
}