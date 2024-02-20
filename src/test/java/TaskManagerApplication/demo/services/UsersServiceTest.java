package TaskManagerApplication.demo.services;

import TaskManagerApplication.demo.data.User;
import TaskManagerApplication.demo.initializer.UserInitializer;
import TaskManagerApplication.demo.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    @Mock
    private UsersRepository usersRepository;

    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
       this.passwordEncoder = new BCryptPasswordEncoder();
        usersService = new UsersService(usersRepository, passwordEncoder);
    }
    @Test
    public void addUserSuccess() {
        User newUser = UserInitializer.createUser();
        when(usersService.addUser(newUser)).thenReturn(newUser);
        User savedUser = usersService.addUser(newUser);

        Assertions.assertEquals(savedUser, newUser);
    }

    @Test
    public void addUserFailure() {
        User newUser = UserInitializer.createUser();
        when(usersService.addUser(newUser))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> usersService.addUser(newUser));
    }


}
