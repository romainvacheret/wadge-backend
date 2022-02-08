package wadge.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import wadge.FakeRepository;
import wadge.model.food.Food;
import wadge.service.food.FoodService;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class FoodControllerTest {
    @MockBean
    private FoodService foodService;
    private FoodController underTest;

    static final List<String> MonthList = List.of("JANUARY", "FEBRUARY", "MARCH",
            "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "DECEMBER");

    @BeforeEach
    void setUp() {
        underTest = new FoodController(foodService);
        when(foodService.getAllFood()).thenReturn(FakeRepository.getFood());
        //when()

    }

    @Test
    void getAllFood() {
        underTest.getAllFood();
        verify(foodService).getAllFood();
    }

    @Test
    void getFoodFromMonth(){
        MonthList.stream().forEach(MonthAsString -> {
            final Month month = Month.valueOf(MonthAsString) ;
            underTest.getFoodFromMonth(MonthAsString);
            verify(foodService).getFoodFromGivenMonth(month);
        });
    }

    @Test
    void getFoodFromMonthByDays(){
        underTest.getFoodFromMonthByDays("JULY");
        verify(foodService).sortByDays(underTest.getFoodFromMonth("JULY"));
    }

    @Test
    @Ignore
    void convert() {
    }
}
