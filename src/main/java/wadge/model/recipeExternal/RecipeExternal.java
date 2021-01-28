package wadge.model.recipeExternal;

public class RecipeExternal {
	String link ; //recipe-card-link link of recipe
	String titre ;//class :recipe-card__title |namerecipe <span class="recipe-card__add-to-notebook connectedUsersOnly
	String rating; //class="recipe-card__rating__value"
	String ratingFract; //  class="recipe-card__rating__value__fract"
	String discret; //class="mrtn-font-discret">
	String description; //recipe-card__description">
	String duration; //class="recipe-card__duration"
	public static  RecipeExternal instance;
	private RecipeExternal(){}
	public static RecipeExternal getInstance(){
		if(instance==null)
			instance=new RecipeExternal();
		return instance;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public String  getRatingFract() {
		return ratingFract;
	}
	
	public void setRatingFract(String  ratingFract) {
		this.ratingFract = ratingFract;
	}
	
	public String getDiscret() {
		return discret;
	}
	
	public void setDiscret(String discret) {
		this.discret = discret;
	}
	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	
}
