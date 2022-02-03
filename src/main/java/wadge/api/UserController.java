package wadge.api;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wadge.model.data.User;
import wadge.service.user.UserService;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService service;
    private final ObjectMapper mapper;

    @PostMapping("/users")
    public void createUsers(@RequestBody final JsonNode node) {
        final List<User> users = Arrays.asList(this.mapper.convertValue(node, User[].class));
        service.createUsers(users);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return service.getUsers();
    }
}
