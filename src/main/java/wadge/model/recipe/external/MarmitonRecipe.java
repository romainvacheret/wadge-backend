package wadge.model.recipe.external;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarmitonRecipe {
	private String link;
	private String name;
	private String ratingfract;
	private String opinion;
	private List<String> steps;
	private String preparation;
	private String servings;
	private String difficulty;
	private List<Ingredient> ingredients;
	private String rating;

	int fromStringToInt(final String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	int convertDifficulty(final String difficulty) {
		return switch(difficulty.toLowerCase()) {
			case "facile" -> 2;
			case "très facile" -> 1;
			case "moyenne" -> 3;
			case "difficile" -> 4;
			default -> -1;
		};
	}

	public Recipe toRecipe() {
		return Recipe.builder()
			.link(link)
			.name(name)
			.rating(fromStringToInt(rating))
			.preparation(fromStringToInt(preparation))
			.servings(fromStringToInt(servings))
			.difficulty(convertDifficulty(difficulty))
			.steps(steps)
			.ingredients(ingredients)
			.build();
	}
}
