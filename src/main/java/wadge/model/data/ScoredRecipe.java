package wadge.model.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wadge.model.recipe.Recipe;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScoredRecipe {
    private Recipe recipe;
    private int score;
}
