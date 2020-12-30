package wadge.recipe.impl;

public class Ingredient {
    private String name;
    private String quantity;

    public Ingredient() {}
    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "Ingredient [name=" + name + ", quantity=" + quantity + "]";
    }
    
}
