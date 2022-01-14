package wadge.model.fridge;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResponse {
    private UUID id;
    private String fridgeFood;
}
