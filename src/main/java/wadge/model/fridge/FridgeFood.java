package wadge.model.fridge;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
// @JsonIgnoreProperties(value={ "getProducts2" })
// @JsonDeserialize(builder=FridgeFoodBuilder.class)
@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class FridgeFood {
    @EqualsAndHashCode.Exclude
    private @Id Long id;
    private String name;
    private Map<Long, FoodElement> products;

    public FridgeFood(String name, Map<Long, FoodElement> products) {
        this.name = name;
        this.products = products;
    }

    // @JsonDeserialize
    // public List<FoodElement> getProducts() { 
    //     return this.products.values().stream().toList();
    // }

    // @JsonIgnore
    // public Map<Long, FoodElement> getProducts2() { return this.products; }

    public void addProduct(FoodElement element) {
        products.put(element.getId(), element);
    }

    public void addAllProducts(List<FoodElement> elements) {
        elements.stream().forEach(this::addProduct);
    }
}
