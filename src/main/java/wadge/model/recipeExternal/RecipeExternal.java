package wadge.model.recipeExternal;

import wadge.model.recipe.Ingredient;

import java.util.List;
import java.util.Objects;

public class RecipeExternal {
	String link ;
	String name ;
	String ratingfract;
	String avis;
	List<String> steps;
	String preparation;
	private String servings;
	private String difficulty;
	private List<Ingredient> ingredients;
	public RecipeExternal(){}
	public  RecipeExternal(String name, List<String> steps, String servings, String duration, String difficulty, String link,String avis, String ratingfract, List<Ingredient> ingredients) {
		this.name = name;
		this.steps = steps;
		this.servings = servings;
		this.preparation = duration;
		this.difficulty = difficulty;
		this.link = link;
		this.ingredients = ingredients;
		this.avis=avis;
		this.ratingfract=ratingfract;
	}
	public String getServings() {
		return servings;
	}
	
	public void setServings(String servings) {
		this.servings = servings;
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
	
	public String getAvis() {
		return avis;
	}

	
	
	public void setAvis(String avis) {
		this.avis = avis;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RecipeExternal)) return false;
		RecipeExternal that = (RecipeExternal) o;
		return Objects.equals(getLink(), that.getLink()) &&
				Objects.equals(getName(), that.getName()) &&
				Objects.equals(getRatingfract(), that.getRatingfract()) &&
				Objects.equals(getAvis(), that.getAvis()) &&
				Objects.equals(getSteps(), that.getSteps()) &&
				Objects.equals(getPreparation(), that.getPreparation()) &&
				Objects.equals(getServings(), that.getServings()) &&
				Objects.equals(getDifficulty(), that.getDifficulty()) &&
				Objects.equals(getIngredients(), that.getIngredients());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getLink(), getName(), getRatingfract(), getAvis(), getSteps(), getPreparation(), getServings(), getDifficulty(), getIngredients());
	}
	
	@Override
	public String toString() {
		return "RecipeExternal{" +
				"link='" + link + '\'' +
				", name='" + name + '\'' +
				", ratingfract='" + ratingfract + '\'' +
				", avis='" + avis + '\'' +
				", steps='" + steps + '\'' +
				", preparation='" + preparation + '\'' +
				", servings='" + servings + '\'' +
				", difficulty='" + difficulty + '\'' +
				", ingredients=" + ingredients +
				'}';
	}
}
