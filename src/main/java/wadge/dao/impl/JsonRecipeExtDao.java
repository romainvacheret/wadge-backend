package wadge.dao.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.springframework.stereotype.Repository;

import wadge.dao.api.IRecipeExternalDao;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.model.recipeExternal.RecipeExternal;
@Repository("jsonRecipeExtDao")
public class JsonRecipeExtDao implements IRecipeExternalDao {
	ObjectMapper mapper;
	Map<String,RecipeExternal> recipeExternals;
	static final String  FILE_NAME="recipeExternal.json";
	static final String BASE_URL="https://www.marmiton.org/recettes/recherche.aspx?aqt=";
	static final String URL="https://www.marmiton.org";
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
	}

	@Override
	public List<RecipeExternal> recipeExternalsFromUrl(String search){
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
					HtmlAnchor link = htmlItem.getFirstByXPath("./a[@class='recipe-card-link']");
					HtmlElement name =htmlItem.getFirstByXPath(".//h4[@class='recipe-card__title']");
					HtmlElement ratingValue = htmlItem.getFirstByXPath(".//div[@class='recipe-card__rating__score']/span[@class='recipe-card__rating__value']");
					HtmlElement avis = htmlItem.getFirstByXPath(".//div[@class='recipe-card__rating']/span[@class='mrtn-font-discret']");
					HtmlElement duration = htmlItem.getFirstByXPath(".//div[@class='recipe-card__duration']/span[@class='recipe-card__duration__value']");
					HtmlElement ratingfract =  htmlItem.getFirstByXPath(".//div[@class='recipe-card__rating__score']/span[@class='recipe-card__rating__value__fract']");
					RecipeExternal recipe = new RecipeExternal();
					List<Ingredient> ingredientList=new ArrayList<>();
					recipe.setLink(link.getHrefAttribute());
					recipe.setPreparation(duration.asText());
					recipe.setRatingfract(ratingfract.asText());
					recipe.setDifficulty(ratingValue.asText());
					recipe.setLink(link.getHrefAttribute());
					recipe.setName(name.asText());
					recipe.setAvis(avis.asText());
					HtmlPage pagerecipe = client.getPage(URL + link.getHrefAttribute());
					HtmlElement serving = pagerecipe.getFirstByXPath("//div[@class='recipe-infos']//div[@class='recipe-infos__quantity']/span[@class='title-2 recipe-infos__quantity__value']");
					recipe.setServings(serving.asText());
					List<DomText> x = pagerecipe.getByXPath("//ol[@class='recipe-preparation__list']//li[@class='recipe-preparation__list__item']/text()");
					System.out.println(x);
					System.out.println("----");
					System.out.println(x.get(0).getClass());
					System.out.println("----");
					List<String> xx = x.stream().map(DomText::asText).collect(Collectors.toList());
					System.out.println("-------xx------------");
					System.out.println(xx);
					System.out.println(xx.get(0).getClass());
					System.out.println("---------------------");
					// String steps =  pagerecipe.getByXPath("//ol[@class='recipe-preparation__list']//li[@class='recipe-preparation__list__item']/text()").toString();
					String steps = x.toString();
					recipe.setServings(serving.asText());
					recipe.setSteps(xx);
					List<String> qt=new ArrayList<>();
					List<String> names=new ArrayList<>();
					List<HtmlElement> listrecipeInfo = pagerecipe.getByXPath("//ul[@class='recipe-ingredients__list']//li[@class='recipe-ingredients__list__item']//span[@class='recipe-ingredient-qt']");
					listrecipeInfo.stream().forEach(htmlElement->qt.add(htmlElement.asText()));
					List<HtmlElement> listname=	pagerecipe.getByXPath("//ul[@class='recipe-ingredients__list']//li[@class='recipe-ingredients__list__item']//span[@class='ingredient']");
					listname.forEach(htmlElement -> names.add(htmlElement.asText()));
					for(int i=0;i<qt.size();i++){ingredientList.add(new Ingredient(names.get(i),qt.get(i)));}
					recipe.setIngredients(ingredientList);
					recipeExternals.put(recipe.getLink(), recipe);
				}
				writeRecipeExternal();
			}
		}catch(Exception e){
			System.out.println(e);
			logger.log(Level.FINE, e.getMessage(), e);
		}
		return recipeExternals.values().stream().collect(Collectors.toList());
	}

	private int timeToMinutes(String time) {
		String arr[] = time.split(" ");
		String arr2[] = arr[0].split("h");
		if(arr2.length == 2) {
			return Integer.valueOf(arr2[0]) * 60 + Integer.valueOf(arr2[1]);
		} else {
			return Integer.valueOf(arr2[0]);
		}
	}

	public List<Recipe> toRecipe(List<RecipeExternal> recipes) {
		return recipes.stream().map(recipe -> new Recipe(
			recipe.getName(),
			recipe.getSteps(),
			Integer.valueOf(recipe.getServings()),
			timeToMinutes(recipe.getPreparation()),
			Double.valueOf(recipe.getDifficulty()),
			recipe.getLink(),
			recipe.getIngredients()
		)).collect(Collectors.toList());
	}
	
	
}
