package br.com.ismadrade.authuser.unit.controllers;


import br.com.ismadrade.authuser.dtos.UserDto;
import br.com.ismadrade.authuser.enums.UserStatus;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @BeforeEach
    private void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Find a user by id.")
    void findUserTest() throws Exception {
        UserModel userSaved = buildUser();
        BDDMockito.given(userService.findById(Mockito.any(UUID.class))).willReturn(Optional.of(userSaved));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/users/9bbc8b99-7057-475b-81ca-6130e15bf030")
                .accept(MediaType.APPLICATION_JSON);
//        String contentAsString = mockMvc.perform(request).andReturn().getResponse().getContentAsString();
        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value(userSaved.getUserId().toString()))
                .andExpect(jsonPath("userStatus").value(UserStatus.ACTIVE.toString()))
                .andExpect(jsonPath("creationDate").isNotEmpty())
                .andExpect(jsonPath("username").value("ismadrade"))
                .andExpect(jsonPath("email").value(userSaved.getEmail()))
                .andExpect(jsonPath("cpf").value(userSaved.getCpf()));
    }

    @Test
    @DisplayName("Throw error by not find an user.")
    void throwErrorByNotFindUserTest() throws Exception {
        BDDMockito.given(userService.findById(Mockito.any(UUID.class))).willReturn(Optional.empty());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/users/9bbc8b99-7057-475b-81ca-6130e15bf030")
                .accept(MediaType.APPLICATION_JSON);
//        String contentAsString = mockMvc.perform(request).andReturn().getResponse().getContentAsString();
        mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Usuario não encontrado"));
    }

    @Test
    @DisplayName("Throw error by giving a wrong old password.")
    void throwErrorByGivingWrongOldPasswordTest() throws Exception {
        UserDto userDto = buildUserDtoToUpdatePassword();
        String json = new ObjectMapper().writeValueAsString(userDto);
        BDDMockito.given(userService.findById(Mockito.any(UUID.class))).willReturn(Optional.of(buildUser()));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/users/9bbc8b99-7057-475b-81ca-6130e15bf030/password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
//        String contentAsString = mockMvc.perform(request).andReturn().getResponse().getContentAsString();
        mockMvc
                .perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value("Senha antiga é incompatível!"));
    }


    private UserDto buildUserDtoToUpdatePassword(){
        return UserDto.builder()
                .password("123456789")
                .oldPassword("1234")
                .build();
    }

    private UserModel buildUser(){
        return UserModel.builder()
                .userId(UUID.fromString("9bbc8b99-7057-475b-81ca-6130e15bf030"))
                .username("ismadrade")
                .email("ismadrade@gmail.com")
                .cpf("09663221447")
                .fullName("Ismael Andrade")
                .userStatus(UserStatus.ACTIVE)
                .password("123456")
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .phoneNumber("48998765432")
                .build();

    }
}
