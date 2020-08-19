package com.prime.task;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.prime.task.model.User;
import com.prime.task.repository.UserRepository;
import com.prime.task.utils.UserStatus;
import com.prime.task.view.model.UserViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private static final String URI_CREATE = "/api/users/create";
    private static final String URI_UPDATE = "/api/users/update";
    private static final String URI_DELETE = "/api/users/delete";

    @Test
    void createUser() throws Exception {
        UserViewModel user = new UserViewModel(null, "A B", "_ab", UserStatus.OFFLINE);

        MockHttpServletResponse response = mockMvc.perform(post(URI_CREATE)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andReturn().getResponse();

        boolean statusCheck = response.getStatus() == CREATED.value();
        assertTrue(statusCheck);

        User createdUser = userRepository.findByLogin("_ab");
        assertNotNull(createdUser);
    }

    @Test
    void updateUser() throws Exception {
        //user for testing
        User user = userRepository.findByLogin("jack");

        //creating custom viewModel object as client-side data
        UserViewModel userViewModel = new UserViewModel(
                user.getId(), user.getFullname(), user.getLogin(), user.getStatus());
        userViewModel.fullname = "Andy Berkley";

        MockHttpServletResponse response = mockMvc.perform(put(URI_UPDATE)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userViewModel)))
                .andReturn().getResponse();

        boolean statusCheck = response.getStatus() == OK.value();
        assertTrue(statusCheck);

        User updatedUser = userRepository.findByLogin("jack");
        assertNotEquals(user.getFullname(), updatedUser.getFullname());
    }

    @Test
    void deleteUser() throws Exception {
        User user = userRepository.findByLogin("jack");

        mockMvc.perform(delete(URI_DELETE + "/{id}", user.getId()))
                .andExpect(status().isOk());

        User deletedUser = userRepository.findByLogin("jack");

        assertNull(deletedUser);
    }
}
