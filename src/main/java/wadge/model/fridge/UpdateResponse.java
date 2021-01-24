package wadge.model.fridge;

import java.util.UUID;

public class UpdateResponse {
    private UUID id;
    private String fridgeFood;
    private int quantity;
    
    public UpdateResponse() {}

    public UpdateResponse(UUID id, String fridgeFood, int quantity) {
        this.id = id;
        this.fridgeFood = fridgeFood;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFridgeFood() {
        return fridgeFood;
    }

    public void setFridgeFood(String fridgeFood) {
        this.fridgeFood = fridgeFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
