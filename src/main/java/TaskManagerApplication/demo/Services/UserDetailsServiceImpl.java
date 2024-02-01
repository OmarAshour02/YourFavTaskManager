package TaskManagerApplication.demo.Services;

import TaskManagerApplication.demo.Data.User;
import TaskManagerApplication.demo.Data.UserDetailsImpl;
import TaskManagerApplication.demo.Repositories.UsersRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    UsersRepository usersRepository;
    @Autowired
    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = usersRepository.findByEmail(email).orElseThrow(IllegalStateException::new); // Handle potential empty Optional
        } catch (IllegalStateException e) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user);
    }
}
