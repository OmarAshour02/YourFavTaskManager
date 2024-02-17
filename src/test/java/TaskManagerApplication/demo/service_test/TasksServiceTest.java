package TaskManagerApplication.demo.service_test;

import TaskManagerApplication.demo.initializer.TaskInitializer;
import TaskManagerApplication.demo.repositories.TasksRepository;
import TaskManagerApplication.demo.services.TasksService;
import TaskManagerApplication.demo.data.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TasksServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksService tasksService;

    @Test
    void addTask() {
        Task task = TaskInitializer.createTask();
        when(tasksRepository.save(task)).thenReturn(task);
        Task savedTask = tasksService.addTask(task);
        verify(tasksRepository).save(task);
        assertThat(savedTask).isEqualTo(task);
    }

    @Test
    void getTask() {
        Task task = TaskInitializer.createTask();
        when(tasksRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        Task foundTask = tasksService.getTask(1L);
        verify(tasksRepository).findById(1L);
        assertThat(foundTask).isEqualTo(task);
    }

    @Test
    void getTasks() {
        tasksService.getTasks();
        verify(tasksRepository).findAll();
    }

    @Test
    void deleteTask() {
        tasksService.deleteTask(1L);
        verify(tasksRepository).deleteById(1L);
    }

    @Test
    void updateTask() {
        Task task = TaskInitializer.createTask();
        when(tasksRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        when(tasksRepository.save(task)).thenReturn(task);
        Task preUpdatedTask = TaskInitializer.createTask(task);

        task.setTitle("Updated Title");

        tasksService.updateTask(task);
        verify(tasksRepository).findById(1L);
        verify(tasksRepository).save(task);

        assertThat(preUpdatedTask).isNotEqualTo(task);
    }

    @Test
    void getTasksByStatus() {
    }

//    @Test
//    void getTasksByPriority() {
//        tasksService.getTasksByPriority('L');
//        verify(tasksRepository).findByPriority('L', pageable);
//    }

    @Test
    void getTasksByUserId() {
        Pageable pageable = PageRequest.of(0, 5);
        tasksService.getTasksByUserId(1L, pageable)
        verify(tasksRepository).findByUserId(1L, pageable);
    }

}
