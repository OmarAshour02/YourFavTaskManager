package TaskManagerApplication.demo.Services;

import TaskManagerApplication.demo.Data.Task;
import TaskManagerApplication.demo.Repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TasksService {
    @Autowired
    TasksRepository tasksRepository;

    public TasksService(TasksRepository tasksRepository){
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
}
