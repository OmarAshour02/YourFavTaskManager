package TaskManagerApplication.demo.services;

import TaskManagerApplication.demo.data.Implementations.UserDetailsImpl;
import TaskManagerApplication.demo.initializer.TaskInitializer;
import TaskManagerApplication.demo.repositories.TasksRepository;
import TaskManagerApplication.demo.data.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TasksServiceTest {

    @Mock
    private UserDetailsImpl userDetails;

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksService tasksService;

    @Test
    void addTask() {
        Task task = TaskInitializer.createTask();
        when(tasksRepository.save(task)).thenReturn(task);
        Task savedTask = tasksService.addTask(userDetails, task);
        verify(tasksRepository).save(task);
        assertThat(savedTask).isEqualTo(task);
    }

    @Test
    void getTask() {
        Task task = TaskInitializer.createTask();
        when(tasksRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        Optional<Task> foundTask = tasksService.getTask(1L);
        verify(tasksRepository).findById(1L);
        assertThat(foundTask).isEqualTo(Optional.of(task));
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
        Pageable pageable = PageRequest.of(0, 5);
        tasksService.getTasksByStatus(1L, true, pageable);
        verify(tasksRepository).findByUserIdAndStatus(1L, true, pageable);
    }

    @Test
    void getTasksByPriority() {
        Pageable pageable = PageRequest.of(0, 5);
        tasksService.getTasksByPriority(1L, 'A', pageable);
        verify(tasksRepository).findByUserIdAndPriority(1L, 'A', pageable);
    }

    @Test
    void getTasksByUserId() {
        Pageable pageable = PageRequest.of(0, 5);
        tasksService.getTasksByUserId(1L, pageable);
        verify(tasksRepository).findByUserId(1L, pageable);
    }

}
