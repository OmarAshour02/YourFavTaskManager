package TaskManagerApplication.demo.service_test;

import TaskManagerApplication.demo.data.User;
import TaskManagerApplication.demo.initializer.UserInitializer;
import TaskManagerApplication.demo.repositories.UsersRepository;
import TaskManagerApplication.demo.services.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
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

    // Let's complete from here
    @Test
    public void getUserSuccess() {
        User newUser = UserInitializer.createUser();
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        System.out.println(encodedPassword);
        when(usersService.getUser(newUser.getEmail(), encodedPassword)).thenReturn(Optional.of(newUser));

        Optional<Object> user = usersService.getUser(newUser.getEmail(), encodedPassword);

        System.out.println(user);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(newUser, user.get());
    }

    @Test
    public void getUserFailure() {
        User newUser = UserInitializer.createUser();
        when(usersService.getUser(newUser.getEmail(), newUser.getPassword())).thenReturn(java.util.Optional.empty());
//        when(bCryptPasswordEncoder.matches(newUser.getPassword(), encodedPassword)).thenReturn(true);
        Optional<Object> optionalUser = usersService.getUser(newUser.getEmail(), newUser.getPassword());
        System.out.println(optionalUser);
//        Assertions.assertTrue(optionalUser.isPresent());
//        Assertions.assertEquals(newUser, optionalUser.get());

    }

    @Test
    public void getSignedInUserId() {
        User newUser = UserInitializer.createUser();
        when(usersService.getSignedInUserId()).thenReturn(newUser.getId());
        Long id = usersService.getSignedInUserId();
        assertEquals(id, newUser.getId());
    }

    @Test
    public void getSignedInUserIdFailure() {
        when(usersService.getSignedInUserId()).thenThrow(NullPointerException.class);
        Assertions.assertThrows(NullPointerException.class, () -> usersService.getSignedInUserId());
    }


}
