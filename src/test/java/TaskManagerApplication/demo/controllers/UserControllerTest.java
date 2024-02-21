package TaskManagerApplication.demo.controllers;


import TaskManagerApplication.demo.data.User;
import TaskManagerApplication.demo.initializer.UserInitializer;
import TaskManagerApplication.demo.services.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @MockBean
    private UsersService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void signUpTestSuccess() throws Exception {

        User user = UserInitializer.createUser();
        when(userService.addUser(user)).thenReturn(user);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/users/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void signUpTestFailure() throws Exception {

        User user = UserInitializer.createUser();
        when(userService.addUser(user)).thenThrow(IllegalArgumentException.class);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/users/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)));
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    public void signInTestSuccess() throws Exception {

        User user = UserInitializer.createUser();
        when(userService.getUser(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));

        ResultActions resultActions = mockMvc.perform(post("/api/v1/users/signin")
                .param("email", user.getEmail())
                .param("password", user.getPassword()));
        resultActions.andExpect(status().isOk());
    }

}
