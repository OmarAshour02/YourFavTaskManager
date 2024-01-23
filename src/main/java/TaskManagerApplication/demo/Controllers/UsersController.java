package TaskManagerApplication.demo.Controllers;


import TaskManagerApplication.demo.Data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import TaskManagerApplication.demo.Services.UsersService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Controller
public class UsersController {
    private final UsersService usersService;
    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User newUser = usersService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestParam String username, @RequestParam String password) {
        Optional<User> user = usersService.getUser(username, password);

        if (user.isPresent()) {
            return ResponseEntity.ok("Sign-in successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
