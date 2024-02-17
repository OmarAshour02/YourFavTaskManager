package TaskManagerApplication.demo.configurations.aspects;

import TaskManagerApplication.demo.data.Implementations.UserDetailsImpl;
import TaskManagerApplication.demo.services.UsersService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class AuthorizationAspect {
    private final UsersService userService;

    public AuthorizationAspect(UsersService userService) {
        this.userService = userService;
    }

    @Before("@annotation(TaskManagerApplication.demo.configurations.annotation.AuthorizeUser)")
    public void authorizeUser(JoinPoint joinPoint) {
        /// Get the method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        // Get the user id from the method arguments
        Long userId = (Long) args[0];


        // Get the signed in user id
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long currentUserId = userDetails.getId();

        if (!Objects.equals(userId, currentUserId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this operation");
        }
    }
}
