package wadge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wadge.dao.impl.JsonRecipeExtDao;


@SpringBootApplication //(scanBasePackages = {"wadge.service", "wadge.dao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // decomenter pour voir le json sur la console
        JsonRecipeExtDao r= new JsonRecipeExtDao();
        r.recipeExternalsFromUrl("tomate");
        
    }
}
