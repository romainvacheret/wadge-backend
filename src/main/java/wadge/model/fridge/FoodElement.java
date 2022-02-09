package wadge.model.fridge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodElement {
        @EqualsAndHashCode.Exclude
        private long id;
        private String insertionDate;
        private String peremptionDate;
        private int quantity;
}
