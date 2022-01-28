package wadge.api;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import java.time.Month;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.service.food.FoodService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FoodControllerTest {
    @Mock
    private FoodService foodService;
    private FoodController underTest;

    static final List<String> monthList = List.of("JANUARY", "FEBRUARY", "MARCH",
            "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "DECEMBER");

    @BeforeEach
    void setUp() {
        underTest = new FoodController(foodService);
    }

    @Test
    void getAllFood() {
        underTest.getAllFood();
        verify(foodService).getAllFood();
    }

    @Test
    public void getFoodFromMonth(){
        monthList.stream().forEach(monthAsString -> {
            final Month month = Month.valueOf(monthAsString) ;
            underTest.getFoodFromMonth(monthAsString);
            verify(foodService).getFoodFromGivenMonth(month);
        });
    }

    // TODO Add values to improve tests

    @Test
    public void getFoodFromMonthException() {
        assertEquals(List.of(), underTest.getFoodFromMonth("test"));
    }

    @Test
    @Ignore
    public void getFoodFromMonthByDays(){
    }

    @Test
    @Ignore
    void convert() {
    }
}
