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
    private UUID id;
    private String fridgeFood;
    private int quantity;
}
