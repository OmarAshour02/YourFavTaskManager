package TaskManagerApplication.demo.Services;

import TaskManagerApplication.demo.Data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import TaskManagerApplication.demo.Repositories.UsersRepository;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User addUser(User user){
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        return usersRepository.save(user);
    }

    public Optional<User> getUser(String userName, String password){
        return usersRepository.findByUsername(userName)
                .filter(user -> user.getPassword().equals(password));
    }
}
