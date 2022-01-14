package wadge.utils.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import wadge.dao.RecipeRepository;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.utils.db.SequenceGenerator;

@Configuration
@AllArgsConstructor
public class RecipeConfig {
    private SequenceGenerator sequenceGenerator;
    
    @Bean
    CommandLineRunner recipeCommandLineRunner(final RecipeRepository repository) {
        return args -> {
            if(repository.findAll().isEmpty()) {
                repository.saveAll( 
					List.of(Recipe.builder()
						.id(sequenceGenerator.generateSequence("recipe_sequence"))
						.name("Soupe d'automne à la courge de butternut")
						.steps(List.of(
							"Faire revenir ensemble dans une cocotte, avec une cuillère d'huile d'olive, l'oignon, le céleri et le navet émincés.",
							"Faire revenir ensemble dans une poêle haute, carottes, courge et pomme de terre, épluchées et coupées en petites cubes. Dès que c'est grillé, verser ces légumes dans la cocotte.",
							"Verser 1.25 litre d'eau froide.",
							"Ajouter le bouillon cube et une petite cuillerée de cumin.",
							"Dès le bouillonnement de l'eau, baisser le feu et laisser cuire 20, 25 min.",
							"En cours de cuisson, ajouter une poignée de persil frisé émincé.",
							"En fin de cuisson, mixer le tout et vérifier l'assaisonnement."
						))
						.servings(3)
						.preparation(45)
						.difficulty(1)
						.rating(4.8)
						.link("https://www.marmiton.org/recettes/recette_soupe-d-automne-courge-butternut_84058.aspx")
						.ingredients(List.of(
							Ingredient.builder()
								.name("carotte")
								.quantity("2")
								.build(),
							Ingredient.builder()
								.name("butternut")
								.quantity("0.5")
								.build(),
							Ingredient.builder()
								.name("pomme de terre")
								.quantity("1")
								.build(),
							Ingredient.builder()
								.name("oignon")
								.quantity("1")
								.build(),
							Ingredient.builder()
								.name("celery")
								.quantity("0.75")
								.build(),
							Ingredient.builder()
								.name("navet")
								.quantity("1")
								.build(),
							Ingredient.builder()
								.name("persil")
								.quantity("-1")
								.build(),
							Ingredient.builder()
								.name("boullion cube")
								.quantity("-1")
								.build(),
							Ingredient.builder()
								.name("cumin")
								.quantity("-1")
								.build()
						)).build())
				);
            }
        };
    }
}
