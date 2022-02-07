package wadge.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.AllArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Service;
import wadge.dao.UserRepository;
import wadge.model.data.ScoredRecipe;
import wadge.model.data.User;
import wadge.model.recipe.Recipe;
import wadge.utils.db.SequenceGenerator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

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

   public List<ScoredRecipe> getScoredRecipesForGivenUser(final String name) {
       final Optional<User> user =  getUsers().stream()
        .filter(u -> u.getName().equals(name)).findFirst();


       return user.isEmpty() ? List.of() : user.get().getRecipes();
   }

   public List<Recipe> computeKnn() throws IOException {
       final HttpClient client = HttpClients.createDefault();
       final HttpPost post = new HttpPost("http://localhost:5000/knn");
       final ObjectMapper mapper = new ObjectMapper();
       final String usersAsString = mapper.writeValueAsString(getUsers());
       final Header headers[] = {
               new BasicHeader("Content-type", "application/json"),
               new BasicHeader("Accept", "application/json")
       };
       final String bodyAsString = String.format("{\"target\": 1, \"users\":%s}", usersAsString);

       post.setHeaders(headers);
       post.setEntity(new StringEntity(bodyAsString, "UTF-8"));

       final HttpResponse response = client.execute(post);
       final HttpEntity entity = response.getEntity();
       final String result = new String(entity.getContent().readAllBytes(), StandardCharsets.UTF_8);
       final CollectionType listRecipeType = mapper.getTypeFactory()
           .constructCollectionType(List.class, Recipe.class);
       final List<Recipe> recipes = mapper.readValue(result, listRecipeType);

       return recipes;
   }
}
