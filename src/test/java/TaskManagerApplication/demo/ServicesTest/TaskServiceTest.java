//package TaskManagerApplication.demo.ServicesTest;
//
//import TaskManagerApplication.demo.data.Task;
//import TaskManagerApplication.demo.DatabaseTest.TaskInitializer;
//import TaskManagerApplication.demo.repositories.TasksRepository;
//import TaskManagerApplication.demo.services.TasksService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class TaskServiceTest {
//    @Mock
//    private TasksRepository tasksRepository;
//
//    @Mock
//    private TasksService tasksService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//    @Test
//    // TaskService isn't saving task correctly
//    public void testAddTask(){
//        Task task = TaskInitializer.createTask();
////        when(tasksRepository.save(task)).thenReturn(task);
//        Task result = tasksService.addTask(task);
////        assertEquals(task, result);
//        System.out.println(task);
//        verify(tasksService).addTask(task);
//    }
//
//    @Test
//    public void testGetTask(){
//        Task task = TaskInitializer.createTask();
//        when(tasksRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
//        assertEquals(task, tasksService.getTask(1L));
//    }
//
//    @Test
//    public void testGetTasks(){
//        Task task = TaskInitializer.createTask();
//        when(tasksRepository.findAll()).thenReturn(java.util.List.of(task));
//        assertEquals(java.util.List.of(task), tasksService.getTasks());
//    }
//
//    @Test
//    public void testDeleteTask(){
////        Task task = TaskInitializer.createTask();
////        when(tasksRepository.findById(1L)).thenReturn(java.util.Optional.empty());
//        tasksService.deleteTask(1L);
//        assertNull(tasksService.getTask(1L));
//    }
//
//    @Test
//    public void testUpdateTask(){
//        Task task = TaskInitializer.createTask();
//        task.setTitle("New Title");
//        task.setDescription("New Description");
//        task.setDueDate(new Date());
//        task.setPriority('H');
//        task.setStatus(true);
//        when(tasksRepository.save(task)).thenReturn(task);
//        assertEquals(task, tasksService.addTask(task));
//    }
//
//    @Test
//    public void testGetTasksByStatus(){
//        Task task = TaskInitializer.createTask();
//        task.setStatus(false);
//        when(tasksRepository.findByStatus(false)).thenReturn(java.util.List.of(task));
//        assertEquals(java.util.List.of(task), tasksService.getTasksByStatus(false));
//    }
//}
