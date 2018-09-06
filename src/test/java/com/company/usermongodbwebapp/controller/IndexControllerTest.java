package com.company.usermongodbwebapp.controller;

import com.company.usermongodbwebapp.model.User;
import com.company.usermongodbwebapp.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    @Mock
    UserService userService;

    @Mock
    Model model;

    IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(userService);
    }

    @Test
    public void TestMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();


        when( userService.getUsers() ).thenReturn( Flux.empty() );

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        //given
        Set<User> users = new HashSet<>();
        users.add(new User());
        User user2 = new User();
        User user3 = new User();
        user2.setId("1");
        user3.setId("2");
        users.add(user2);
        users.add(user3);

        when( userService.getUsers() ).thenReturn( Flux.fromIterable( users ) );

        ArgumentCaptor<List<User>> captor = ArgumentCaptor.forClass(List.class);

        //when
        String properName = indexController.getIndexPage(model);

        //then
        assertEquals("index", properName );
        verify(userService, times(1) ).getUsers();
        verify( model, times(1) ).addAttribute( eq("users" ), captor.capture() );
        List<User> list = captor.getValue();
        assertEquals(3, list.size());
    }
}