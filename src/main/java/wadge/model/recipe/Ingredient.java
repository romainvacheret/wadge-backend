package wadge.model.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    private String name;
    @EqualsAndHashCode.Exclude
    private String quantity;

    public enum Unit { KG, G, NONE }

    public static Unit getUnit(String name) {
        Unit rtr = Unit.NONE;
        if(name.startsWith("kg de")) {
            rtr = Unit.KG;
        } else if(name.startsWith("g de")) {
            rtr = Unit.G;
        }

        return rtr;
    }

    public static String extractName(Ingredient ingredient) {
        String[] arr = ingredient.getName().split(" de ");
        return arr[arr.length - 1];
    }
}
