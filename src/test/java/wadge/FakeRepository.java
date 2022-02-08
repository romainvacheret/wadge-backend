package wadge;

import wadge.model.food.Food;
import wadge.model.recipe.Ingredient;
import wadge.model.recipe.Recipe;
import wadge.model.recipe.RecipeTag;

import java.time.Month;
import java.util.List;
import java.util.Set;

public class FakeRepository {
    public static List<Food> getFood() {
        return List.of(Food.builder()
            .id(1)
            .name("fraise")
            .type("fruit")
            .availability(List.of(Month.APRIL, Month.MAY, Month.JUNE))
            .days(3)
            .weight(100)
            .bulk(true)
            .build(),
            Food.builder()
                .id(2)
                .name("haricot")
                .type("legume")
                .availability(List.of(Month.JULY, Month.AUGUST, Month.SEPTEMBER))
                .days(4)
                .weight(100)
                .bulk(true)
                .build(),
            Food.builder()
                .id(3)
                .name("mais")
                .type("legume")
                .availability(List.of(Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER))
                .days(2)
                .weight(200)
                .bulk(false)
                .build(),
            Food.builder()
                .id(4)
                .name("mangue")
                .type("fruit")
                .availability(List.of(Month.JANUARY, Month.FEBRUARY, Month.DECEMBER))
                .days(8)
                .weight(500)
                .bulk(false)
                .build());
    }

    public static List<Recipe> getRecipes() {
        return List.of(Recipe.builder()
                        .id(1)
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
                        .tags(Set.of(RecipeTag.FAVORITE))
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
                        )).build(),
                Recipe.builder()
                        .id(2)
                        .name("Tagliatelles aux aspèrgesSoupe d'automne à la courge de butternut")
                        .steps(List.of(
                                "Eplucher et couper en petits tronçons les aspèrges", "Chauffer 2 cuil. à soupe de beurre dans un casserole, mettre les aspèrge, couvrir et cuire 20 minutes",
                                "Délayer le cube de bouillon dans l'eau chaude puis verser sur les aspèrges. Saler, Poivrer et laisser cuire encore 3 minutes",
                                "Cuire les pâtes dans un grand volume d'eau salée, la moitié du temps indiqué", "Egoutter les pâtes, ajouter le reste du beurre et la moitié du parmesan",
                                "Préchauffer le four à 180°C", "Beurrer les bords et le fond d'un plat allant au four", "Verser une couche de tagliatelles, puis une couche d'aspèrges, soupoudrer de parmesan. Repéter l'opération autant de fois que nécéssaire",
                                "Battre les oeufs avec le reste du parmesan dans un bol", "Napper les pates avec ce mélange", "Enfourner pour 20 minutes, puis servir immédiatement"
                        ))
                        .servings(4)
                        .preparation(55)
                        .difficulty(2)
                        .rating(4)
                        .link("")
                        .tags(Set.of())
                        .ingredients(List.of(
                                Ingredient.builder()
                                        .name("tagliatelle")
                                        .quantity("500g")
                                        .build(),
                                Ingredient.builder()
                                        .name("asperges")
                                        .quantity("800g")
                                        .build(),
                                Ingredient.builder()
                                        .name("oeufs")
                                        .quantity("2")
                                        .build(),
                                Ingredient.builder()
                                        .name("parmesan")
                                        .quantity("125g")
                                        .build(),
                                Ingredient.builder()
                                        .name("cube de bouillon")
                                        .quantity("-1")
                                        .build(),
                                Ingredient.builder()
                                        .name("verre d'eau chaude")
                                        .quantity("0.5")
                                        .build(),
                                Ingredient.builder()
                                        .name("beurre")
                                        .quantity("125g")
                                        .build(),
                                Ingredient.builder()
                                        .name("c.a.s. huile olive")
                                        .quantity("1")
                                        .build(),
                                Ingredient.builder()
                                        .name("Sel et Poivre")
                                        .quantity("-1")
                                        .build()
                        )).build());
    }

}
