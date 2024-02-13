package TaskManagerApplication.demo.controllers;

import TaskManagerApplication.demo.data.Task;
import TaskManagerApplication.demo.services.TasksService;
import TaskManagerApplication.demo.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/tasks")
public class TasksController {

    private final TasksService tasksService;
    private final UsersService usersService;
    @Autowired
    public TasksController(TasksService tasksService, UsersService usersService) {
        this.tasksService = tasksService;
        this.usersService = usersService;
    }

    @PostMapping("/{userId}")
    public Task addTask(@PathVariable Long userId, @RequestBody Task task) {
        task.setUserId(userId);
        return tasksService.addTask(task);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        Task task = tasksService.getTask(id);
        if(!Objects.equals(task.getUserId(), usersService.getSignedInUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this task");
        }
        return tasksService.getTask(id);
    }

    @GetMapping("/all/{userId}")
    public List<Task> getTasks(@PathVariable Long userId) {
        Long signedInUserId = usersService.getSignedInUserId();
        if(!Objects.equals(userId, signedInUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this task");
        }
        return tasksService.getTasksByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
    }

    @PutMapping
    public Task updateTask(@RequestBody Task task) {
        return tasksService.updateTask(task);
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable boolean status) {
        return tasksService.getTasksByStatus(status);
    }

    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable char priority) {
        return tasksService.getTasksByPriority(priority);
    }
}
