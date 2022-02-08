package wadge.model.fridge;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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

    public void addProduct(final FoodElement element) {
        products.put(element.getId(), element);
    }

    public void addAllProducts(final List<FoodElement> elements) {
        elements.stream().forEach(this::addProduct);
    }
}
