package wadge.model.recipe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @EqualsAndHashCode.Exclude
    private @Id long id;
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
    private Set<RecipeTag> tags = new HashSet<>();
}