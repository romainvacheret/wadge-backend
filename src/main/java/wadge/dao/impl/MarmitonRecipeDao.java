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
				List<HtmlElement> recipes = page.getByXPath("//div[@class='CardResultstyle__Layout-sc-1cct3mj-1 kIyuTI']");
				List<HtmlElement> opinion = page.getByXPath("//div[@class='RecipeCardResultstyle__RatingNumber-sc-30rwkm-3 jtNPhW']");
				List<HtmlElement> ratingValue= page.getByXPath("//div[@class='RatingStarstyle__Rating-sc-11eskgd-0 joYcJz']/span[@class='MuiTypography-root MuiTypography-caption']");
				List<String> rt=new ArrayList<>();
				ratingValue.stream().forEach(htmlElement -> rt.add(htmlElement.asText()));
				List<String> op=new ArrayList<>();
				opinion.stream().forEach(htmlElement -> op.add(htmlElement.asText()));
				List<HtmlAnchor> recipesLink = page.getByXPath("//div[@class='SearchResultsstyle__Layout-sc-1gofnyi-0 fuwQUX']//a[@class='SearchResultsstyle__SearchCardResult-sc-1gofnyi-2 gQpFpv']");
				List<String> linkList = new ArrayList<>();
				List<String> steps = new ArrayList<>();
				List<String> preparation = new ArrayList<>();
				List<String> serving = new ArrayList<>();
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
						List<String> qt=new ArrayList<>();
						List<String> mames=new ArrayList<>();
						List<HtmlElement> q=pageLink.getByXPath("//div[@class='item-list__item']/div[@class='item__quantity show-icon']//span[@class='quantity']");
						List<HtmlElement>  n= pageLink.getByXPath("//div[@class='item-list__item']/div[@class='item__ingredient']//span[@class='ingredient-name show-icon']");
						q.stream().forEach(qte->qt.add(qte.asText()));
						n.stream().forEach(nme-> mames.add(nme.asText()));
						List<Ingredient> ingrediens = new ArrayList<>();
						for (int j = 0; j < nmes.size(); j++) { ingrediens.add(new Ingredient( mames.get(j), qt.get(j)));}
						ingredients.put(i,ingrediens);
						i++;
						
					}
					System.out.println("la longeur"+i);
				}
				if (!recipes.isEmpty()) {
					int i = 0;
					for (HtmlElement htmlItem : recipes) {
						System.out.println("dans le deuxieme for des recettes");
						MarmitonRecipe recipe = new MarmitonRecipe();
						recipe.setLink(linkList.get(i));
						recipe.setName(nmes.get(i));
						recipe.setOpinion(op.get(i));
						String[] rting = rt.get(i).split("/");
						recipe.setRating(rting[0]);
						recipe.setRatingfract("/5");
						recipe.setSteps(Arrays.asList(steps.get(i).split("\\.")));
						String prepa=preparation.get(i).replace("Temps Total : ","");
						recipe.setPreparation(prepa);
						recipe.setDifficulty(difficluty.get(i));
					    // recipe.setIngredients(ingredients.get(i));
					    //recipe.setServings(serving.get(i));
						recipeExternals.put(recipe.getLink(), recipe);
						i++;
						System.out.println("la recette"+recipe);
						//recipeExternals.put(recipe.getLink(), recipe);
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
