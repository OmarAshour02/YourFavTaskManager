package TaskManagerApplication.demo.controllers;

import TaskManagerApplication.demo.configurations.annotation.AuthorizeUser;
import TaskManagerApplication.demo.data.Implementations.UserDetailsImpl;
import TaskManagerApplication.demo.data.Task;
import TaskManagerApplication.demo.services.TasksService;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/tasks")
public class TasksController {

    /**
     * The TasksService to handle the business logic
     *
     *
     */
    private final TasksService tasksService;
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping({"/"})
    public ResponseEntity<Task> addTask(@AuthenticationPrincipal UserDetailsImpl userDetails , @RequestBody Task task) {
        tasksService.addTask(userDetails, task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/{id}")
    @AuthorizeUser
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Optional<Task> task = tasksService.getTask(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getTasks(@AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) {
        List<Task> tasks = tasksService.getTasksByUserId(userDetails.getId(), pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable boolean status, Pageable pageable){
        List<Task> tasks = tasksService.getTasksByStatus(userDetails.getId(), status, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable char priority, Pageable pageable){
        List<Task> tasks = tasksService.getTasksByPriority(userDetails.getId(), priority, pageable);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    @AuthorizeUser
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        tasksService.updateTask(task);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(task);
    }

}
