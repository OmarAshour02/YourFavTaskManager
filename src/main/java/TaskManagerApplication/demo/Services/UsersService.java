package TaskManagerApplication.demo.Services;

import TaskManagerApplication.demo.Data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import TaskManagerApplication.demo.Repositories.UsersRepository;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User addUser(User user) throws IllegalArgumentException{
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public Optional<User> getUser(String userName, String password){
        return usersRepository.findByUsername(userName)
                .filter(user -> bCryptPasswordEncoder.matches(password, user.getPassword()));
    }
}
