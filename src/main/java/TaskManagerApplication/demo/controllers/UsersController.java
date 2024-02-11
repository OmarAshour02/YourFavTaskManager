package TaskManagerApplication.demo.controllers;


import TaskManagerApplication.demo.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import TaskManagerApplication.demo.services.UsersService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Controller
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody User user){
        try {
            User newUser = usersService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Long> signIn(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = usersService.getUser(email, password);
        System.out.println(user.isPresent());
        return user.map(value -> ResponseEntity.ok(value.getId())).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

    }

    @PostMapping("/signout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }
}

