package wadge.dao.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import wadge.dao.api.IRecipeExternalDao;
import wadge.model.recipeExternal.RecipeExternal;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
@Repository("jsonRecipeExtDao")
public class JsonRecipeExtDao implements IRecipeExternalDao {
	ObjectMapper mapper;
	final String  FILE_NAME="recipeExternal.json";
	final String baseUrl="https://www.marmiton.org/recettes/recherche.aspx?aqt=";
	@Override
	public void writeRecipeExternal(String jsonString) {
		try {
			mapper.writeValue(Paths.get(FILE_NAME).toFile(), jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JsonRecipeExtDao(){
		mapper=new ObjectMapper();
	}
	@Override
	public List<RecipeExternal> readExternalRecipe(){
		List<RecipeExternal> 	recipeExternals=new ArrayList<>();
		try {
			recipeExternals.addAll(Arrays.asList(mapper.readValue(Paths.get(FILE_NAME).toFile(), RecipeExternal.class)));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return recipeExternals;
	}

	@Override
	public void recipeExternalsFromUrl(String search){
		try {
			WebClient client = new WebClient();
			client.getOptions().setCssEnabled(false);
			client.getOptions().setJavaScriptEnabled(false);
			HtmlPage page=client.getPage(baseUrl+ URLEncoder.encode(search, "UTF-8"));
			List<HtmlElement> recipes=page.getByXPath("//div[@class='recipe-card']");
			if (recipes.isEmpty()) {
				System.out.println("No items found !");
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
					writeRecipeExternal(jsonString);
					System.out.println(jsonString);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
