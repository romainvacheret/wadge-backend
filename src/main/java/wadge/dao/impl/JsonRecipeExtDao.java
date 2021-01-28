package wadge.dao.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import wadge.dao.api.IRecipeExternalDao;
import wadge.model.recipeExternal.RecipeExternal;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
@Repository("jsonRecipeExtDao")
public class JsonRecipeExtDao implements IRecipeExternalDao {
	ObjectMapper mapper;
	Map<String,RecipeExternal> recipeExternals;
	List<RecipeExternal> recipes;
	static final String  FILE_NAME="recipeExternal.json";
	static final String BASE_URL="https://www.marmiton.org/recettes/recherche.aspx?aqt=";
	private static Logger logger = Logger.getLogger(JsonRecipeExtDao.class.getName());
	@Override
	public void writeRecipeExternal() {
		try {
			mapper.writeValue(Paths.get(FILE_NAME).toFile(), recipeExternals.values());
		} catch (IOException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}
	
	public JsonRecipeExtDao(){
		mapper=new ObjectMapper();
		recipeExternals=new HashMap<>();
		recipes=new ArrayList<>();
	}

	@Override
	public void recipeExternalsFromUrl(String search){
		try {
			WebClient client = new WebClient();
			client.getOptions().setCssEnabled(false);
			client.getOptions().setJavaScriptEnabled(false);
			HtmlPage page=client.getPage(BASE_URL+ URLEncoder.encode(search, "UTF-8"));
			List<HtmlElement> recipes=page.getByXPath("//div[@class='recipe-card']");
			if (recipes.isEmpty()) {
			System.out.println("recipe not found");
			} else {
				for (HtmlElement htmlItem : recipes) {
					HtmlAnchor link= ((HtmlAnchor )htmlItem.getFirstByXPath("./a[@class='recipe-card-link']"));
					HtmlElement itemAnchor = ((HtmlElement) htmlItem.getFirstByXPath(".//h4[@class='recipe-card__title']"));
					HtmlElement ratingValue = ((HtmlElement) htmlItem.getFirstByXPath(".//div[@class='recipe-card__rating__score']/span[@class='recipe-card__rating__value']"));
					HtmlElement spandiscret = ((HtmlElement) htmlItem.getFirstByXPath(".//div[@class='recipe-card__rating']/span[@class='mrtn-font-discret']"));
					HtmlElement description = ((HtmlElement) htmlItem.getFirstByXPath(".//div[@class='recipe-card__description']"));
					HtmlElement duration = ((HtmlElement) htmlItem.getFirstByXPath(".//div[@class='recipe-card__duration']/span[@class='recipe-card__duration__value']"));
					HtmlElement ratingFract=((HtmlElement) htmlItem.getFirstByXPath(".//div[@class='recipe-card__rating__score']/span[@class='recipe-card__rating__value__fract']"));
					RecipeExternal recipe= RecipeExternal.getInstance();
					recipe.setLink(link.getHrefAttribute());
					recipe.setRatingFract(ratingFract.asText());
					recipe.setRating(ratingValue.asText());
					recipe.setDescription(description.asText());
					recipe.setDuration(duration.asText());
					recipe.setTitre(itemAnchor.asText());
					recipe.setDiscret(spandiscret.asText());
					String jsonString = mapper.writeValueAsString(recipe);
					System.out.println(jsonString);
					recipeExternals.put(recipe.getLink(),recipe);
					//
				}
				writeRecipeExternal();
			}
		}catch(Exception e){
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	
}
