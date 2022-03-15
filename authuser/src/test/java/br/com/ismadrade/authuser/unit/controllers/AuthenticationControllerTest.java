package br.com.ismadrade.authuser.unit.controllers;

import br.com.ismadrade.authuser.dtos.UserDto;
import br.com.ismadrade.authuser.enums.UserStatus;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

@WebAppConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationControllerTest {

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
    @DisplayName("Create a user")
    void createUserTest() throws Exception {
        UserDto userDto = buildUserDto();
        UserModel userSaved = buildUser();
        BDDMockito.given(userService.saveUser(Mockito.any(UserModel.class))).willReturn(userSaved);
        String json = new ObjectMapper().writeValueAsString(userDto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        //String contentAsString = mockMvc.perform(request).andReturn().getResponse().getContentAsString();
        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("userStatus").value(UserStatus.ACTIVE.toString()))
                .andExpect(jsonPath("creationDate").isNotEmpty())
                .andExpect(jsonPath("username").value("ismadrade"))
                .andExpect(jsonPath("email").value(userDto.getEmail()))
                .andExpect(jsonPath("cpf").value(userDto.getCpf()));
    }

    @Test
    @DisplayName("Throw error when registering an existing username")
    void throwErrorSameUsername() throws Exception {
        UserDto userDto = buildUserDto();
        String json = new ObjectMapper().writeValueAsString(userDto);
        String mensagemErro = "Usuario já cadastrado!";
        BDDMockito.given(userService.existsByUsername(Mockito.any(String.class))).willReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value(mensagemErro))
                .andExpect(jsonPath("httpStatus").value("CONFLICT"));
    }

    @Test
    @DisplayName("Throw error when registering an existing e-mail")
    void throwErrorSameEmail() throws Exception {
        UserDto userDto = buildUserDto();
        String json = new ObjectMapper().writeValueAsString(userDto);
        String mensagemErro = "E-mail já cadastrado!";
        BDDMockito.given(userService.existsByEmail(Mockito.any(String.class))).willReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value(mensagemErro))
                .andExpect(jsonPath("httpStatus").value("CONFLICT"));
    }

    @Test
    @DisplayName("Throw error when registering an existing CPF")
    void throwErrorSameCpf() throws Exception {
        UserDto userDto = buildUserDto();
        String json = new ObjectMapper().writeValueAsString(userDto);
        String mensagemErro = "CPF já cadastrado!";
        BDDMockito.given(userService.existsByCpf(Mockito.any(String.class))).willReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value(mensagemErro))
                .andExpect(jsonPath("httpStatus").value("CONFLICT"));
    }


    private UserDto buildUserDto(){
        return UserDto.builder()
                .username("Ismadrade")
                .email("ismadrade@gmail.com")
                .cpf("09663221447")
                .fullName("Ismael Andrade")
                .password("123456")
                .phoneNumber("48998765432")
                .build();
    }

    private UserModel buildUser(){
        return UserModel.builder()
                .userId(UUID.fromString("166404d8-9d86-11ec-b909-0242ac120002"))
                .username("Ismadrade")
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
