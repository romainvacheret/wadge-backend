package wadge.model.food;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wadge.service.food.FoodHelper.Conversion;

@Getter
@Setter
@NoArgsConstructor
public class ConversionRequest {
    private Conversion type;
    private double quantity;
    private String food;
}
