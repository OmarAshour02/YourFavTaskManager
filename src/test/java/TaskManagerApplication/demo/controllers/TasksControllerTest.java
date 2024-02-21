package TaskManagerApplication.demo.controllers;

import TaskManagerApplication.demo.data.Implementations.UserDetailsImpl;
import TaskManagerApplication.demo.data.Task;
import TaskManagerApplication.demo.initializer.TaskInitializer;
import TaskManagerApplication.demo.initializer.UserInitializer;
import TaskManagerApplication.demo.services.TasksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = TasksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TasksControllerTest {

    @MockBean
    private TasksService tasksService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        UserDetailsImpl userDetails = new UserDetailsImpl(UserInitializer.createUser());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
    }

    @Test
    void addTaskReturnsTrue() throws Exception  {
        when(tasksService.addTask(any(UserDetailsImpl.class), any(Task.class))).thenReturn(TaskInitializer.createTask());
        ResultActions response = mockMvc.perform(post("/api/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(tasksService.addTask(userDetails, TaskInitializer.createTask()))));
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void addTaskReturnsFalse() throws Exception {
        when(tasksService.addTask(any(UserDetailsImpl.class), any(Task.class))).thenReturn(null);
        ResultActions response = mockMvc.perform( post("/api/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(tasksService.addTask(userDetails, TaskInitializer.createTask()))));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getTaskReturnsTrue() throws Exception {
        when(tasksService.getTask(1L)).thenReturn(java.util.Optional.of(TaskInitializer.createTask()));
        ResultActions response = mockMvc.perform(get("/api/v1/tasks/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(tasksService.getTask(1L))));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getTaskReturnsFalse() throws Exception {
        when(tasksService.getTask(1L)).thenReturn(java.util.Optional.empty());
        ResultActions response = mockMvc.perform(get("/api/v1/tasks/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(tasksService.getTask(1L))));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void getTasksReturnsTrue() throws Exception {
        when(tasksService.getTasksByUserId(userDetails.getId(), null)).thenReturn(List.of(TaskInitializer.createTask()));
        ResultActions response = mockMvc.perform(get("/api/v1/tasks/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(tasksService.getTasksByUserId(userDetails.getId(), null))));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getTasksByStatusReturnsTrue() throws Exception {
        when(tasksService.getTasksByStatus(userDetails.getId(), true, null)).thenReturn(List.of(TaskInitializer.createTask()));
        ResultActions response = mockMvc.perform(get("/api/v1/tasks/status/{status}", true)
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(tasksService.getTasksByStatus(userDetails.getId(), true, null))));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getTasksByPriorityReturnsTrue() throws Exception {
        when(tasksService.getTasksByPriority(userDetails.getId(), 'A', null)).thenReturn(List.of(TaskInitializer.createTask()));
        ResultActions response = mockMvc.perform(get("/api/v1/tasks/priority/{priority}", 'A')
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(tasksService.getTasksByPriority(userDetails.getId(), 'A', null))));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateTaskReturnsTrue() throws Exception {
        when(tasksService.updateTask(TaskInitializer.createTask())).thenReturn(TaskInitializer.createTask());
        ResultActions response = mockMvc.perform(post("/api/v1/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(tasksService.updateTask(TaskInitializer.createTask()))));
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
