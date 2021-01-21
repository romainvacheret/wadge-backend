package wadge.model.fridge;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder=FridgeFoodBuilder.class)
public class FridgeFood {
    private UUID id = UUID.randomUUID();
    private String name;
    private Map<UUID, FoodElement> products;
    // private List<FoodElement> products;

    public FridgeFood() {}

    // public FridgeFood(String name, List<FoodElement> products) {
    //     System.out.println("ICI");
    //     this.name = name;
    //     this.products = products.stream().map(product -> Map.entry(product.getId(), product))
    //         .filter(e -> e.getKey().isPresent()).map(e -> Map.entry(e.getKey().get(), e.getValue()))
    //         .collect(Collectors.toMap(Map.Entry<UUID, FoodElement>::getKey, Map.Entry<UUID, FoodElement>::getValue));
    // }

    // public FridgeFood(UUID id, String name, List<FoodElement> products) {
    //     this(name, products);
    //     System.out.println("LA");
    //     this.id = id;
    // }

    public FridgeFood(String name, Map<UUID, FoodElement> products) {
        this.name = name;
        this.products = products;
    }

    public FridgeFood(UUID id, String name, Map<UUID, FoodElement> products) {
        this(name, products);
        if(id != null) {
            this.id = id;
        }
    }

    public UUID getId() { return id; }
    public String getName() { return this.name; }
    // @JsonDeserialize
    // public List<FoodElement> getProducts() { return products; }
    public List<FoodElement> getProducts() { System.out.println("1111111111"); return this.products.values().stream().collect(Collectors.toList()); }
    // public Map<UUID, FoodElement> getProducts2() { System.out.println("22222222"); return this.products; }

    public void setId(UUID id) { this.id = id; }

    public void addProduct(FoodElement element) {
        products.put(element.getId(), element);
    }

    public void addAllProcucts(List<FoodElement> elements) {
        elements.stream().forEach(food -> addProduct(food));
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((products == null) ? 0 : products.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FridgeFood other = (FridgeFood) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (products == null) {
            if (other.products != null)
                return false;
        } else if (!products.equals(other.products))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FridgeFood [id=" + id + ", name=" + name + ", products=" + products + "]";
    }
    
}
