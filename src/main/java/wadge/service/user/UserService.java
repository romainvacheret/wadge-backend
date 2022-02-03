package wadge.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wadge.dao.UserRepository;
import wadge.model.data.User;
import wadge.utils.db.SequenceGenerator;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
   private final UserRepository repository;
   private final SequenceGenerator sequenceGenerator;

   public List<User> getUsers() {
       return repository.findAll();
   }

   public void createUsers(final List<User> users) {
       repository.saveAll(users.stream()
           .peek(user -> user.setId(sequenceGenerator.generateSequence("user_sequence")))
           .toList());
   }
}
