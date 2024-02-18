package TaskManagerApplication.demo.configurations.aspects;

import TaskManagerApplication.demo.data.Implementations.UserDetailsImpl;
import TaskManagerApplication.demo.data.Task;
import TaskManagerApplication.demo.services.TasksService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
public class AuthorizationAspect {
    private final TasksService tasksService;

    public AuthorizationAspect(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @Before("@annotation(TaskManagerApplication.demo.configurations.annotation.AuthorizeUser)")
    public void authorizeUser(JoinPoint joinPoint) {
        /// Get the the current user id from the UserDetailsImpl object
        Object[] args = joinPoint.getArgs();

        Long taskId = (Long) args[0];

        Optional<Task> task = tasksService.getTask(taskId);

        if(task.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        // Get the signed-in user id
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long currentUserId = userDetails.getId();

        if (!Objects.equals(task.get().getUserId(), currentUserId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this operation");
        }
    }
}
