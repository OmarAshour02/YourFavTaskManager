package TaskManagerApplication.demo.ServicesTest;

import TaskManagerApplication.demo.Data.Task;
import TaskManagerApplication.demo.DatabaseTest.TaskInitializer;
import TaskManagerApplication.demo.Repositories.TasksRepository;
import TaskManagerApplication.demo.Services.TasksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {
    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksService tasksService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testAddTask(){
        Task task = TaskInitializer.createTask();
        tasksRepository.save(task);
        verify(tasksRepository).save(task);
        System.out.println("Printing all tasks");
        System.out.println(tasksRepository.findAll().size());
//        when(tasksRepository.save(task)).thenReturn(java.util.Optional.of(task).get());
//        assertEquals(task, tasksService.addTask(task));
    }

    @Test
    public void testGetTask(){
        Task task = TaskInitializer.createTask();
        when(tasksRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        assertEquals(task, tasksService.getTask(1L));
    }

    @Test
    public void testGetTasks(){
        Task task = TaskInitializer.createTask();
        when(tasksRepository.findAll()).thenReturn(java.util.List.of(task));
        assertEquals(java.util.List.of(task), tasksService.getTasks());
    }

    @Test
    public void testDeleteTask(){
//        Task task = TaskInitializer.createTask();
//        when(tasksRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        tasksService.deleteTask(1L);
        assertNull(tasksService.getTask(1L));
    }

    @Test
    public void testUpdateTask(){
        Task task = TaskInitializer.createTask();
        task.setTitle("New Title");
        task.setDescription("New Description");
        task.setDueDate(new Date());
        task.setPriority('H');
        task.setStatus(true);
        when(tasksRepository.save(task)).thenReturn(task);
        assertEquals(task, tasksService.addTask(task));
    }

    @Test
    public void testGetTasksByStatus(){
        Task task = TaskInitializer.createTask();
        task.setStatus(false);
        when(tasksRepository.findByStatus(false)).thenReturn(java.util.List.of(task));
        assertEquals(java.util.List.of(task), tasksService.getTasksByStatus(false));
    }
}
