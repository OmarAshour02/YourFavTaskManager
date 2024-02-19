package TaskManagerApplication.demo.services;

import TaskManagerApplication.demo.data.Implementations.UserDetailsImpl;
import TaskManagerApplication.demo.data.Task;
import TaskManagerApplication.demo.repositories.TasksRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }


    public Task addTask(UserDetailsImpl userDetails, Task task){
        task.setUserId(userDetails.getId());
        return tasksRepository.save(task);
    }
    public Optional<Task> getTask(Long id){
        return tasksRepository.findById(id);
    }


    public List<Task> getTasksByStatus(Long userId, boolean status, Pageable pageable){
        return tasksRepository.findByUserIdAndStatus(userId, status, pageable);
    }

    public List<Task> getTasksByPriority(Long userId, char priority, Pageable pageable){
        return tasksRepository.findByUserIdAndPriority(userId,priority, pageable);
    }

    public List<Task> getTasksByUserId(Long userId, Pageable pageable){
        return tasksRepository.findByUserId(userId, pageable);
    }

    public void deleteTask(Long id){
        tasksRepository.deleteById(id);
    }

    public Task updateTask(@NotNull Task task){
        Optional<Task> taskOptional = tasksRepository.findById(task.getId());
        if(taskOptional.isPresent()){
            Task taskToUpdate = taskOptional.get();
            taskToUpdate.setTitle(task.getTitle());
            taskToUpdate.setDescription(task.getDescription());
            taskToUpdate.setDueDate(task.getDueDate());
            taskToUpdate.setPriority(task.getPriority());
            taskToUpdate.setStatus(task.isStatus());
            return tasksRepository.save(taskToUpdate);
        }
        return null;
    }
}
