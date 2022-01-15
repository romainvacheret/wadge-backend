package wadge.model.fridge;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponse {
    @EqualsAndHashCode.Exclude
    private long id; // FoodElement's Id
    private String fridgeFood;
    private int quantity;
}
