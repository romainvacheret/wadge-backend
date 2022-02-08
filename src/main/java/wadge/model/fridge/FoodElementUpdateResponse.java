package wadge.model.fridge;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodElementUpdateResponse {
    @EqualsAndHashCode.Exclude
    private long id; // FoodElement's Id
    private long fridgeFood;
    private int quantity;
}
