package wadge.model.recipe.external;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wadge.model.recipe.Ingredient;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarmitonRecipe {
	private String link ;
	private String name ;
	private String ratingfract;
	private String opinion;
	private List<String> steps;
	private String preparation;
	private String servings;
	private String difficulty;
	private List<Ingredient> ingredients;
	private String rating;
}
