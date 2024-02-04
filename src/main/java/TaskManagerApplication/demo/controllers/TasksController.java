package TaskManagerApplication.demo.controllers;

import TaskManagerApplication.demo.data.Task;
import TaskManagerApplication.demo.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TasksController {

    private final TasksService tasksService;

    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return tasksService.addTask(task);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return tasksService.getTask(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Task> getTasks() {
        return tasksService.getTasks();
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
