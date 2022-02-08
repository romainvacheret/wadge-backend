package wadge.model.food;

import lombok.*;
import wadge.service.food.FoodHelper.Conversion;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {
    private Conversion type;
    private double quantity;
    private String food;
}
