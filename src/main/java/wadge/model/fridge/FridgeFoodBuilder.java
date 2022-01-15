package wadge.model.fridge;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPOJOBuilder(buildMethodName="createFridgeFood", withPrefix="with")
public class FridgeFoodBuilder {
    private Long id;
    private String name;
    private Map<UUID, FoodElement> products;

    public FridgeFoodBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public FridgeFoodBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FridgeFoodBuilder withProducts(List<FoodElement> food) {
        /*this.products = food.stream().map(product -> Map.entry(product.getId(), product))
            .collect(Collectors.toMap(Map.Entry<UUID, FoodElement>::getKey, Map.Entry<UUID, FoodElement>::getValue));


         */
        return this;
    }

    public FridgeFood createFridgeFood() {
        return null;
        // return id != null ? new FridgeFood(id, name, products) : new FridgeFood(name, products);
    }
}
