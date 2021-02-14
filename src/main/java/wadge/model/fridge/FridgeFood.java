package wadge.model.fridge;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
@JsonIgnoreProperties(value={ "getProducts2" })
@JsonDeserialize(builder=FridgeFoodBuilder.class)
public class FridgeFood {
    private UUID id = UUID.randomUUID();
    private String name;
    private Map<UUID, FoodElement> products;

    public FridgeFood() {}

    public FridgeFood(String name, Map<UUID, FoodElement> products) {
        this.name = name;
        this.products = products;
    }

    public FridgeFood(UUID id, String name, Map<UUID, FoodElement> products) {
        this(name, products);
        if(this.id != null) {
            this.id = id;
        }
    }

    public UUID getId() { return id; }
    public String getName() { return this.name; }
    @JsonDeserialize
    public List<FoodElement> getProducts() { return this.products.values().stream().collect(Collectors.toList()); }
    @JsonIgnore
    public Map<UUID, FoodElement> getProducts2() { return this.products; }

    public void setId(UUID id) { this.id = id; }

    public void addProduct(FoodElement element) {
        products.put(element.getId(), element);
    }

    public void addAllProducts(List<FoodElement> elements) {
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
