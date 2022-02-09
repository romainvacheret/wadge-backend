package wadge.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import wadge.dao.RecipeRepository;
import wadge.dao.UserRepository;
import wadge.utils.db.SequenceGenerator;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@DataMongoTest
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RecipeRepository recipeRepository;
    @Mock
    private SequenceGenerator sequenceGenerator;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(
            userRepository,
            recipeRepository,
            sequenceGenerator);
    }

    @Test
    void getUsers() {
        underTest.getUsers();
        verify(userRepository).findAll();
    }
}