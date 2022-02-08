package wadge.model.fridge;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class LoadedFridgeFood {
    private String name;
    private List<FoodElement> products;

    public FridgeFood toFridgeFood(final long id) {
        return FridgeFood.builder()
            .name(name)
            .products(products.stream()
                .map(element -> {
                    element.setId(id);
                    return element;
                })
                .collect(Collectors.toMap(
                    FoodElement::getId,
                    element -> element))
            ).build();
    }
}
