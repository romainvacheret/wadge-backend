package wadge.model.fridge;

import java.util.UUID;

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
        private UUID id = UUID.randomUUID();
        private String insertionDate;
        private String peremptionDate;
        private int quantity;

        public FoodElement(String insertionDate, String peremptionDate, int quantity) {
            this.id = UUID.randomUUID();
            this.insertionDate = insertionDate;
            this.peremptionDate = peremptionDate;
            this.quantity = quantity;
        }
}
