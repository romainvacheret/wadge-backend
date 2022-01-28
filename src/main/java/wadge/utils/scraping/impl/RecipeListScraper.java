package wadge.utils.scraping.impl;

import wadge.model.recipe.external.MarmitonRecipe;
import wadge.utils.scraping.model.AbstractPageScraper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class RecipeListScraper extends AbstractPageScraper {
    private static final String recipeListXPath = "//*[@id=\"__next\"]/div[3]/main/div/div[2]/div[1]/div[1]/div[3]";
    private static final String urlXPath = "//*[@id=\"__next\"]/div[3]/main/div/div[2]/div[1]/div[1]/div[3]/a[%d]";
    private static final String marmitonUrl = "\"https://www.marmiton.org%s\"";

    public RecipeListScraper(final String url) throws IOException {
        super(url);
    }

    private Optional<MarmitonRecipe> scrapRecipe(final String url) {
        System.out.println("ici");
        System.out.println(url);
        try {
            return Optional.ofNullable(new RecipeScraper(url).scrap());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private List<String> scrapRecipesLink() {
        final int recipeCount = doc.selectXpath(recipeListXPath).get(0).childrenSize();
        return IntStream.range(1, recipeCount + 1)
            .boxed()
            .map(idx -> getCompletedXPath(urlXPath, idx))
            .map(doc::selectXpath)
            .map(element -> String.format(
                marmitonUrl,
                    element.first().attr("href")).replace("\"", ""))
            .toList();
    }

    @Override
    public List<MarmitonRecipe> scrap() {
        return scrapRecipesLink().stream()
            .map(this::scrapRecipe)
            .flatMap(Optional::stream)
            .toList();
    }

    public static void main(String[] args) throws IOException {
        final RecipeListScraper scraper = new RecipeListScraper("https://www.marmiton.org/recettes/recherche.aspx?aqt=gateau&page=6");
        System.out.println(scraper.scrap());
        List.of("https://www.marmiton.org/recettes/recherche.aspx?aqt=gateau&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=butternut&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=quiche&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=patate-douce&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=poireaux&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=verrine&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=courgette&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=saute-de-porc&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=galette-des-rois&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=chou-vert&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=pomme-terre&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=mascarpone&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=roti-de-porc&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=chou-rouge&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=riz&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=aubergine&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=radis-noir&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=plat-leger&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=chou-blanc&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=fenouil&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=blanquette-de-veau&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=potimarron&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=celeri-rave&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=cabillaud&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=salade&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=rouelle-de-porc&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=cuisses-de-poulet&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=recette-rapide-et-facile&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=nouille-chinoise&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=pois-casses&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=marinade-poulet&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=cr%C3%8Apes&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=chou-chinois&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=wraps&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=haricot-vert&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=poivron&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=cookies&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=bruschetta&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=soupe-au-blender&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=pizza&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=tiramisu&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=dessert-leger&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=banane&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=samoussa&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=rhum-arrang%C3%89&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=recette-cookeo&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=escalope-de-dinde&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=sushi&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=boudin-blanc&page=%d",
                "https://www.marmiton.org/recettes/recherche.aspx?aqt=quiche-lorraine");
    }
}
