package wadge.model.fridge;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FridgeFood {
    private UUID id;
    private String name;
    private List<FoodElement> products;

    public FridgeFood() {}

    public FridgeFood(UUID id, String name, FoodElement[] products) {
        this.id = id;
        this.name = name;
        this.products = Arrays.asList(products);
    }

    public FridgeFood(String name, List<FoodElement> products) {
        this.name = name;
        this.products = products;
    }

    public Optional<UUID> getId() { return Optional.ofNullable(id); }
    public String getName() { return this.name; }
    public List<FoodElement> getProducts() { return this.products; }

    public void setFood(UUID id) { this.id = id; }

    
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
