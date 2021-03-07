package wadge.model.shopping;

public class ShoppingElement {
    private String name;
    private double quantity;

    public ShoppingElement() { /* Needed by Jackson */}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
