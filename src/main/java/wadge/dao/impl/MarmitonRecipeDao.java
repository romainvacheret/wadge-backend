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
	static final String URL = "https://www.marmiton.org/";
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
			List<HtmlAnchor> recipesLink = page.getByXPath("//div[@class='SearchResultsstyle__Layout-sc-1gofnyi-0 fuwQUX']//a[@class='SearchResultsstyle__SearchCardResult-sc-1gofnyi-2 gQpFpv']");
			List<String> linkList = new ArrayList<>();
			List<String> steps = new ArrayList<>();
			List<String> preparation = new ArrayList<>();
			List<String> serving = new ArrayList<>();
			List<String> difficluty = new ArrayList<>();
			Map<Integer, List<Ingredient>> ingredients = new HashMap<>();
			List<String> nmes=new ArrayList<>();
			if (!recipesLink.isEmpty()) {
				
				int i = 0;
				for (HtmlAnchor htmlItem : recipesLink) {
					
					linkList.add(htmlItem.getHrefAttribute());
					HtmlPage pageLink = client.getPage(URL + htmlItem.getHrefAttribute());
					List<HtmlElement> name=pageLink.getByXPath("//p[@class='mrtn-modal-sub-title__container']//span['mrtn-modal-sub-title']");
					nmes.add(name.stream().map(HtmlElement::asText).collect(Collectors.joining()));
					List<HtmlElement> stp = pageLink.getByXPath("//ol[@class='recipe-preparation__list']");
					String stps = stp.stream().map(HtmlElement::asText).collect(Collectors.joining());
					steps.add(stps);
					List<HtmlElement> p = pageLink.getByXPath("//div[@class='recipe-infos__timmings__total-time title-4']");
					preparation.add(p.stream().map(HtmlElement::asText).collect(Collectors.joining()));
					List<HtmlElement> srv = pageLink.getByXPath("//span[@class='title-2 recipe-infos__quantity__value']");
					serving.add(srv.stream().map(HtmlElement::asText).collect(Collectors.joining()));
					List<HtmlElement> diff = pageLink.getByXPath("//span[@class='recipe-infos__item-title']");
					difficluty.add(diff.stream().map(HtmlElement::asText).collect(Collectors.joining()));
					List<String> names = new ArrayList<>();
					List<String> qt = new ArrayList<>();
					List<HtmlElement> qte = pageLink.getByXPath("//span[@class='recipe-ingredient-qt']");
					qte.stream().forEach(q -> qt.add(q.asText()));
					List<HtmlElement> n = pageLink.getByXPath("//span[@class='ingredient']");
					n.stream().forEach(nme -> names.add(nme.asText()));
					List<Ingredient> ingrediens = new ArrayList<>();
					for (int j = 0; j < names.size(); j++) {
						ingrediens.add(new Ingredient(names.get(j), qt.get(j)));
					}
					ingredients.put(i, ingrediens);
					i++;
				}
			}
			int i = 0;
			if (!recipes.isEmpty()) {
				for (HtmlElement htmlItem : recipes) {
					HtmlElement ratingValue = htmlItem.getFirstByXPath("//span[@class='MuiTypography-root MuiTypography-caption']");
					HtmlElement opinion = htmlItem.getFirstByXPath("//div[@class='RecipeCardResultstyle__RatingNumber-sc-30rwkm-3 jtNPhW']");
					MarmitonRecipe recipe = new MarmitonRecipe();
					recipe.setLink(linkList.get(i));
					recipe.setName(nmes.get(i).replace("une photo",""));
<<<<<<< HEAD
=======

>>>>>>> 1a40a11248b5dac4e21389bd5ec723a3c1230602
					recipe.setOpinion(opinion.asText());
					String[] r = ratingValue.asText().split("/");
					recipe.setRating(r[0]);
					recipe.setRatingfract("/5");
					recipe.setSteps(Arrays.asList(steps.get(i).split("\\.")));
					recipe.setIngredients(ingredients.get(i));
					String prepa=preparation.get(i).replace("Temps Total : ","");
					recipe.setPreparation(prepa);
					recipe.setDifficulty(difficluty.get(i));
					recipe.setServings(serving.get(i));
					recipeExternals.put(recipe.getLink(), recipe);
					i++;
<<<<<<< HEAD
				
=======
					
>>>>>>> 1a40a11248b5dac4e21389bd5ec723a3c1230602
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
