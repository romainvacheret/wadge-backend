package wadge.model.food;

import wadge.service.food.FoodHelper.Conversion;

public class ConversionRequest {
    private Conversion type;
    private double quantity;
    private String food;
    
    
    public ConversionRequest() { /* Needed by Jakson */ }

    public Conversion getType() {
        return type;
    }

    public void setType(Conversion type) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
