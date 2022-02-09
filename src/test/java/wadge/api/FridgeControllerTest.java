package wadge.api;

import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.service.fridge.FridgeService;
import wadge.utils.db.SequenceGenerator;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class FridgeControllerTest {
    @MockBean
    private FridgeService fridgeService;
    @MockBean
    private SequenceGenerator sequenceGenerator;
    private FridgeController underTest;

    @BeforeEach
    void setUp() {
        underTest = new FridgeController(fridgeService, sequenceGenerator);
    }

    @Test
    void getAllFridge() {
        underTest.getAllFridge();
        verify(fridgeService).getAllFridge();
    }

    @Test
    void emptyFridge() {
        underTest.emptyFridge();
        verify(fridgeService).emptyFridge();
    }

    @Test
    void getExpirationAlerts() {
        underTest.getExpirationAlerts();
        Arrays.stream(FridgeService.RecallType.values()).forEach(
            recallType -> verify(fridgeService).getExpirationList(recallType)
        );
    }
}
