package wadge.model.recipeExternal;

import java.util.Objects;

public class RecipeExternal {
	String link ; //recipe-card-link link of recipe
	String titre ;//class :recipe-card__title |namerecipe <span class="recipe-card__add-to-notebook connectedUsersOnly
	String rating; //class="recipe-card__rating__value"
	String ratingFract; //  class="recipe-card__rating__value__fract"
	String discret; //class="mrtn-font-discret">
	String description; //recipe-card__description">
	String duration; //class="recipe-card__duration"
	protected static  RecipeExternal instance;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RecipeExternal)) return false;
		RecipeExternal that = (RecipeExternal) o;
		return Objects.equals(getLink(), that.getLink()) &&
				Objects.equals(getTitre(), that.getTitre()) &&
				Objects.equals(getRating(), that.getRating()) &&
				Objects.equals(getRatingFract(), that.getRatingFract()) &&
				Objects.equals(getDiscret(), that.getDiscret()) &&
				Objects.equals(getDescription(), that.getDescription()) &&
				Objects.equals(getDuration(), that.getDuration());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getLink(), getTitre(), getRating(), getRatingFract(), getDiscret(), getDescription(), getDuration());
	}
	
	@Override
	public String toString() {
		return "RecipeExternal{" +
				"link='" + link + '\'' +
				", titre='" + titre + '\'' +
				", rating='" + rating + '\'' +
				", ratingFract='" + ratingFract + '\'' +
				", discret='" + discret + '\'' +
				", description='" + description + '\'' +
				", duration='" + duration + '\'' +
				'}';
	}
}
