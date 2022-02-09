package wadge.api;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import wadge.service.user.UserService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class UserControllerTest {
    @MockBean
    private UserService userService;
    private UserController underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserController(userService);
    }

    @Test
    void getUsers() {
        underTest.getUsers();
        verify(userService).getUsers();
    }

    @Test
    void computeKnn() {
        underTest.computeKnn();
        verify(userService).computeKnn();
    }

    @Test
    void getScoredRecipesForGivenUser() {
        final ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

        underTest.getScoredRecipesForGivenUser("1");
        verify(userService).getScoredRecipesForGivenUser(stringCaptor.capture());
        assertEquals("1", stringCaptor.getValue());
    }
}