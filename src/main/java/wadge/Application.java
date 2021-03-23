package wadge;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wadge.dao.impl.MarmitonRecipeDao;
import wadge.model.recipe.external.MarmitonRecipe;

import java.util.List;


@SpringBootApplication
public class Application {
    public static MarmitonRecipeDao m=new MarmitonRecipeDao();

    public static void main(String[] args) {
        //view Console ToDo To remove after
    
        //m.recipeNosRecetteFromUrl("tomate)");
        SpringApplication.run(Application.class, args);
     
    }
}
