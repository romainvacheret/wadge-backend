package wadge.dao.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.springframework.stereotype.Repository;
import wadge.dao.api.IExternalRecipeDao;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.model.recipe.external.MarmitonRecipe;
@Repository("jsonRecipeExtDao")
public class MarmitonRecipeDao implements IExternalRecipeDao {
	ObjectMapper mapper;
	Map<String,MarmitonRecipe> recipeExternals;
	static final String FILE_NAME = "recipeExternal.json";
	static final String BASE_URL = "https://www.marmiton.org/recettes/recherche.aspx?aqt=";
	static final String URL = "https://www.marmiton.org";
	private static Logger logger = Logger.getLogger(MarmitonRecipeDao.class.getName());
	@Override
	public void writeRecipeExternal() {
		try {
			mapper.writeValue(Paths.get(FILE_NAME).toFile(), recipeExternals.values());
		} catch (IOException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}
	
	public MarmitonRecipeDao(){
		mapper=new ObjectMapper();
		recipeExternals=new HashMap<>();
	}
	
	@Override
	public List<MarmitonRecipe> recipeExternalsFromUrl(String search) {
		
			try (WebClient client = new WebClient()) {
				
				client.getOptions().setCssEnabled(false);
				client.getOptions().setJavaScriptEnabled(false);
				HtmlPage page = client.getPage(BASE_URL + URLEncoder.encode(search, "UTF-8"));
				List<HtmlElement> rating =page.getByXPath("//span[@class='MuiTypography-root MuiTypography-caption']");
				List<HtmlElement> opinion = page.getByXPath("//div[@class='RecipeCardResultstyle__RatingNumber-sc-30rwkm-3 jtNPhW']");
				List<String> op=new ArrayList<>();
				opinion.forEach(htmlElement -> op.add(htmlElement.asText()));
				List<HtmlAnchor> recipesLink = page.getByXPath("//div[@class='SearchResultsstyle__Layout-sc-1gofnyi-0 fuwQUX']//a[@class='SearchResultsstyle__SearchCardResult-sc-1gofnyi-2 gQpFpv']");
				List<String> linkList = new ArrayList<>();
				List<String> steps = new ArrayList<>();
				List<String> preparation = new ArrayList<>();
				
				List<String> difficluty = new ArrayList<>();
				Map<Integer, List<Ingredient>> ingredients = new HashMap<>();
				List<String> nmes = new ArrayList<>();
				
			
				if (!recipesLink.isEmpty()) {
		
					int i = 0;
					for (HtmlAnchor htmlItem : recipesLink) {
						
						linkList.add(URL + htmlItem.getHrefAttribute());
						HtmlPage pageLink = client.getPage(URL + htmlItem.getHrefAttribute());
						List<HtmlElement> ns = pageLink.getByXPath("//div[@class='mrtn-modal--with-head-picture__title-container']//p[@class='mrtn-modal-sub-title__container']//span['mrtn-modal-sub-title']");
						nmes.add(ns.stream().map(HtmlElement::asText).collect(Collectors.joining()));
						List<HtmlElement> stp = pageLink.getByXPath("//div[@class='recipe-step-list']/div[@class='recipe-step-list__container']");
						String stps = stp.stream().map(HtmlElement::asText).collect(Collectors.joining());
						steps.add(stps);
						HtmlElement prepa=pageLink.getFirstByXPath("//div[@class='recipe-preparation__time']/div[@class='time__total']");
						preparation.add(prepa.asText());
						HtmlElement diff=(HtmlElement) pageLink.getByXPath("//div[@class='recipe-primary']/div[@class='recipe-primary__item']").get(1);
						difficluty.add(diff.asText());
						
						List<HtmlElement> q=pageLink.getByXPath("//div[@class='item__ingredient']//span[@class='ingredient-full show-text']");
						List<HtmlElement>  n= pageLink.getByXPath("//div[@class='item__ingredient']//span[@class='ingredient-name show-icon']");//("//div[@class='item-list__item item-prog']//div[@class='item__ingredient']");
						List<String> qt=new ArrayList<>();
						q.forEach(qte->qt.add(qte.asText()));
						List<String> mames=new ArrayList<>();
						n.forEach(nme->mames.add(nme.asText()) );
						
						List<Ingredient> ingrediens = new ArrayList<>();
						for (int j = 0; j < mames.size(); j++) {
							Pattern p = Pattern.compile("\\d+");
							Matcher m = p.matcher(qt.get(j));
							if(m.find()) {
								ingrediens.add(new Ingredient( mames.get(j), qt.get(j)));
							}
							else ingrediens.add(new Ingredient( mames.get(j), ""));
							
						}
						ingredients.put(i,ingrediens);
						i++;
						
					}
	
				}
				if (!rating.isEmpty()) {
		
					int i = 0;
					for (HtmlElement htmlItem : rating) {
					
						MarmitonRecipe recipe = new MarmitonRecipe();
						recipe.setLink(linkList.get(i));
						recipe.setName(nmes.get(i));
						recipe.setOpinion(op.get(i));
						String[] rting = htmlItem.asText().split("/");
						recipe.setRating(rting[0]);
						recipe.setRatingfract("/5");
						recipe.setSteps(Arrays.asList(steps.get(i).split("\\.")));
						String prepa=preparation.get(i).replace("Temps Total : ","");
						prepa.replace("\n","");
						recipe.setPreparation(prepa);
						recipe.setDifficulty(difficluty.get(i));
					    recipe.setIngredients(ingredients.get(i));
					    recipe.setServings("5");
						recipeExternals.put(recipe.getLink(), recipe);
						i++;
						recipeExternals.put(recipe.getLink(), recipe);
					}
				
					writeRecipeExternal();
				}
			} catch (Exception e) {
				logger.log(Level.FINE, e.getMessage(), e);
			}
		return recipeExternals.values().stream().collect(Collectors.toList());
		}
	
	public List<Recipe> toRecipe(List<MarmitonRecipe> recipes) {
		return recipes.stream().filter(recipe -> !recipe.getPreparation().equals(""))
          .map(recipe -> new Recipe(
			recipe.getName(),
			recipe.getSteps(),
			Integer.valueOf(recipe.getServings()),
			MarmitonRecipeHelper.timeToMinutes(recipe.getPreparation()),
			MarmitonRecipeHelper.convertDifficulty(recipe.getDifficulty()),
			Double.valueOf(recipe.getRating()),
			recipe.getLink(),
			recipe.getIngredients()
		)).collect(Collectors.toList());
	}
}
