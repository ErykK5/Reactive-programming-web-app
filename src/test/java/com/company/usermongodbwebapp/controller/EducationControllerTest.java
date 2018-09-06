package com.company.usermongodbwebapp.controller;

import com.company.usermongodbwebapp.model.Education;
import com.company.usermongodbwebapp.model.User;
import com.company.usermongodbwebapp.services.EducationService;
import com.company.usermongodbwebapp.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class EducationControllerTest {

    @Mock
    UserService userService;

    @Mock
    EducationService educationService;

    EducationController educationController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this );
        educationController = new EducationController(educationService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(educationController).build();
    }

    @Test
    public void testShowEducationList() throws Exception{
        //given
        User u = new User();
        u.setId("1");
        when( userService.findById( any() ) ).thenReturn( Mono.just( u ) );

        //when
        mockMvc.perform( get("/user/educations/1") )
                .andExpect( status().isOk() )
                .andExpect( view().name("user/educations/list") )
                .andExpect( model().attributeExists("user") );

        //then
        verify(userService, times(1) ).findById( any() );
    }

    @Test
    public void testShowSpecificEducation() throws Exception{
        //given
        User u = new User();
        Education e = new Education();
        e.setUser( u );
        e.getUser().setId("1");
        e.setId("2");

        //when
        when( educationService.findByUserIdAndEducationId( any(), any() ) ).thenReturn( Mono.just( e ) );

        //then
        mockMvc.perform( get("/user/educations/show/1/1") )
                .andExpect( status().isOk() )
                .andExpect( view().name("user/educations/show") )
                .andExpect( model().attributeExists("edu") );
    }

    @Test
    public void testUpdateEducation() throws Exception{
        //given
        User u = new User();
        Education e = new Education();
        e.setUser( u );
        e.getUser().setId("1");
        e.setId("2");

        //when
        when( educationService.findByUserIdAndEducationId( any(), any() ) ).thenReturn( Mono.just( e ) );

        //then
        mockMvc.perform( get("/user/educations/update/1/1") )
                .andExpect( status().isOk() )
                .andExpect( view().name("user/educations/update") )
                .andExpect( model().attributeExists("edu") );
    }

    @Test
    public void testNewEducation() throws Exception{
        //given
        Education e = new Education();
        User u = new User();
        u.setId("1");

        //when
        when( userService.findById( any() ) ).thenReturn( Mono.just( u ) );

        //then
        mockMvc.perform( get("/user/educations/new/1") )
                .andExpect( status().isOk() )
                .andExpect( view().name("user/educations/new") )
                .andExpect( model().attributeExists("edu", "user") );
    }

    @Test
    public void testSaveOrUpdateEducation() throws Exception{
        //given
        User u = new User();
        Education e = new Education();
        e.setUser( u );
        u.setId( "1" );
        e.setId( "1" );

        //when
        when( userService.findById( any() ) ).thenReturn( Mono.just( u ) );
        when( educationService.createEducation( any() ) ).thenReturn( Mono.just( e ) );

        //then
        mockMvc.perform( post("/user/educations/1" )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
        )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/user/educations/1") );
    }

    @Test
    public void testDeleteByIdEducation() throws Exception{

        when( educationService.deleteById( anyString(), anyString() ) ).thenReturn( Mono.empty() );


        //then
        mockMvc.perform( get("/user/educations/delete/1/1") )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/user/educations/1"));

        verify(educationService, times(1 ) ).deleteById( anyString(), anyString() );
    }
}