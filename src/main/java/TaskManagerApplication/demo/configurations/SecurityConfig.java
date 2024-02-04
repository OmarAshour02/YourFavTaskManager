package TaskManagerApplication.demo.configurations;

import TaskManagerApplication.demo.services.implementations.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                // Configure form login
                .formLogin(withDefaults())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/api/v1/users/signup"))
//                .oauth2Login(withDefaults())
                // Need to do custom csrf configuration
                .csrf((csrf) -> csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // This is for testing purposes

    //    @Bean
//    public UserDetailsService userDetailsServiceTest() {
//        UserDetailsImpl admin = (UserDetailsImpl) User.builder().username("admin").password(bCryptPasswordEncoder().encode("admin")).roles("ADMIN")
//            .build();
//        return new InMemoryUserDetailsManager(admin);
//    }
}
