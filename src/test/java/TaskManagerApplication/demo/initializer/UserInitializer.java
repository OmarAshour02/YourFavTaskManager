package TaskManagerApplication.demo.initializer;

import TaskManagerApplication.demo.data.User;

public class UserInitializer {
    public static User createUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("mail@mail.com");
        user.setUsername("username");
        user.setRole("USER");
        user.setPassword("password");
        return user;
    }
}
