package wadge.model.fridge;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties(value={ "getProducts2" })
@JsonDeserialize(builder=FridgeFoodBuilder.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFood {
    @EqualsAndHashCode.Exclude
    private UUID id = UUID.randomUUID();
    private String name;
    private Map<UUID, FoodElement> products;

    public FridgeFood(String name, Map<UUID, FoodElement> products) {
        this.name = name;
        this.products = products;
    }

    @JsonDeserialize
    public List<FoodElement> getProducts() { 
        return this.products.values().stream().toList();
    }
    @JsonIgnore
    public Map<UUID, FoodElement> getProducts2() { return this.products; }

    public void addProduct(FoodElement element) {
        products.put(element.getId(), element);
    }

    public void addAllProducts(List<FoodElement> elements) {
        elements.stream().forEach(this::addProduct);
    }
}
