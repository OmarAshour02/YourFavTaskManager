package TaskManagerApplication.demo.services;

import TaskManagerApplication.demo.data.Task;
import TaskManagerApplication.demo.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Task addTask(Task task){
        return tasksRepository.save(task);
    }
    public Task getTask(Long id){
        return tasksRepository.findById(id).orElse(null);
    }

    public List<Task> getTasks(){
        return tasksRepository.findAll();
    }

    public void deleteTask(Long id){
        tasksRepository.deleteById(id);
    }

    public Task updateTask(Task task){
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

    public List<Task> getTasksByStatus(boolean status){
        return tasksRepository.findByStatus(status);
    }

    public List<Task> getTasksByPriority(char priority){
        return tasksRepository.findByPriority(priority);
    }


}
