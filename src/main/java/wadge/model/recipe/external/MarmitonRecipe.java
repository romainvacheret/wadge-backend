package wadge.model.recipe.external;

import java.util.List;
import java.util.Objects;

import wadge.model.recipe.Ingredient;

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
	public MarmitonRecipe() {}

	public MarmitonRecipe(String link,String name, List<String> steps, String servings, String duration, String difficulty, String opinion, String ratingfract,String rating, List<Ingredient> ingredients) {
		this.name = name;
		this.steps = steps;
		this.servings = servings;
		this.preparation = duration;
		this.difficulty = difficulty;
		this.link = link;
		this.ingredients = ingredients;
		this.opinion=opinion;
		this.ratingfract=ratingfract;
		this.rating=rating;
	}

	public String getServings() {
		return servings;
	}
	
	public void setServings(String servings) {
		this.servings = servings;
	}
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String  getRatingfract() {
		return ratingfract;
	}
	
	public void setRatingfract(String  ratingfract) {
		this.ratingfract = ratingfract;
	}
	
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public List<String> getSteps() {
		return steps;
	}
	
	public void setSteps (List<String> steps) {
		this.steps = steps;
	}
	
	public String getPreparation() {
		return preparation;
	}
	public void setPreparation(String duration) {
		this.preparation = duration;
	}
	
	@Override
	public String toString() {
		return "MarmitonRecipe{" +
				"link='" + link + '\'' +
				", name='" + name + '\'' +
				", ratingfract='" + ratingfract + '\'' +
				", opinion='" + opinion + '\'' +
				", steps=" + steps +
				", preparation='" + preparation + '\'' +
				", servings='" + servings + '\'' +
				", difficulty='" + difficulty + '\'' +
				", ingredients=" + ingredients +
				", rating='" + rating + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MarmitonRecipe)) return false;
		MarmitonRecipe that = (MarmitonRecipe) o;
		return Objects.equals(getLink(), that.getLink()) &&
				Objects.equals(getName(), that.getName()) &&
				Objects.equals(getRatingfract(), that.getRatingfract()) &&
				Objects.equals(getOpinion(), that.getOpinion()) &&
				Objects.equals(getSteps(), that.getSteps()) &&
				Objects.equals(getPreparation(), that.getPreparation()) &&
				Objects.equals(getServings(), that.getServings()) &&
				Objects.equals(getDifficulty(), that.getDifficulty()) &&
				Objects.equals(getIngredients(), that.getIngredients()) &&
				Objects.equals(getRating(), that.getRating());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getLink(), getName(), getRatingfract(), getOpinion(), getSteps(), getPreparation(), getServings(), getDifficulty(), getIngredients(), getRating());
	}
}
