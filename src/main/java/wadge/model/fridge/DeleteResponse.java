package wadge.model.fridge;

import java.util.UUID;

public class DeleteResponse {
    private UUID id;
    private String fridgeFood;
    
    public DeleteResponse() {}

    public DeleteResponse(UUID id, String fridgeFood) {
        this.id = id;
        this.fridgeFood = fridgeFood;
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
}
