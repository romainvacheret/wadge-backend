package wadge.api;

import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.service.fridge.FridgeService;
import wadge.utils.db.SequenceGenerator;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class FridgeControllerTest {
    @Mock
    private FridgeService fridgeService;
    @Mock
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
    @Ignore
    void addAllToFridge() {
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

    @Test
    @Ignore
    void updateFromFridge() {
    }
}
