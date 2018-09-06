package com.company.usermongodbwebapp.controller;

import com.company.usermongodbwebapp.model.User;
import com.company.usermongodbwebapp.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    Model model;

    UserController userController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testShowById() throws Exception {
        User u = new User();
        u.setId("1");

        when( userService.findById( anyString() ) ).thenReturn(Mono.just( u ) );

        mockMvc.perform( get("/user/show/1") )
                .andExpect( status().isOk() )
                .andExpect( view().name("user/show") )
                .andExpect( model().attributeExists("user") );
    }

    @Test
    public void testCreateUser() throws Exception {
        User u = new User();

        mockMvc.perform( get("/user/new") )
                .andExpect( status().isOk() )
                .andExpect( view().name("user/userform") )
                .andExpect( model().attributeExists("user") );
    }

    @Test
    public void testSaveUser() throws Exception {
        User u = new User();
        u.setId("1");

        when( userService.createUser( any() ) ).thenReturn( Mono.just( u ) );

        mockMvc.perform( post("/user")
            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
            .param("id","")
            .param("description","some string")
        )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/") );
    }

    @Test
    public void testUpdateUser() throws Exception {
        User u = new User();
        u.setId( "1" );

        when( userService.findById( anyString() ) ).thenReturn( Mono.just( u ) );

        mockMvc.perform(get("/user/update/1"))
                .andExpect( status().isOk() )
                .andExpect( view().name("user/userform") )
                .andExpect( model().attributeExists("user") );
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(get("/user/delete/1") )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/") );
    }
}