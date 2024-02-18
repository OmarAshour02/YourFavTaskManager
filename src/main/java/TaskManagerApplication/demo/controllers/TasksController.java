package TaskManagerApplication.demo.controllers;

import TaskManagerApplication.demo.configurations.annotation.AuthorizeUser;
import TaskManagerApplication.demo.data.Implementations.UserDetailsImpl;
import TaskManagerApplication.demo.data.Task;
import TaskManagerApplication.demo.services.TasksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping({"/"})
    public Task addTask(@AuthenticationPrincipal UserDetailsImpl userDetails , @RequestBody Task task) {
        return tasksService.addTask(userDetails, task);
    }

    @GetMapping("/{id}")
    @AuthorizeUser
    public Optional<Task> getTask(@PathVariable Long id) {
        return tasksService.getTask(id);
    }

    @GetMapping("/all")
    public List<Task> getTasks(@AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable) {
        return tasksService.getTasksByUserId(userDetails.getId(), pageable);
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable boolean status, Pageable pageable){
        return tasksService.getTasksByStatus(userDetails.getId(), status, pageable);
    }

    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable char priority, Pageable pageable){
        return tasksService.getTasksByPriority(userDetails.getId(), priority, pageable);
    }

    @DeleteMapping("/{id}")
    @AuthorizeUser
    public void deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
    }

    @PutMapping
    public Task updateTask(@RequestBody Task task) {
        return tasksService.updateTask(task);
    }

}
