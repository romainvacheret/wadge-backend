package wadge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wadge.dao.impl.MarmitonRecipeDao;


@SpringBootApplication
public class Application {
    @Autowired
    static MarmitonRecipeDao m=new MarmitonRecipeDao();
    public static void main(String[] args) {
        
        SpringApplication.run(Application.class, args);
        //m.recipeExternalsFromUrl("oignon");
      
     
    }
}
