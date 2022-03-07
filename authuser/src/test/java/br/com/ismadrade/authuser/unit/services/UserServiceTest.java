package br.com.ismadrade.authuser.unit.services;


import br.com.ismadrade.authuser.enums.UserStatus;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.repositories.UserRepository;
import br.com.ismadrade.authuser.services.UserService;
import br.com.ismadrade.authuser.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        this.userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("Test save an user")
    public void saveAnUser(){
        UserModel user = buildUser(false);
        Mockito.when(userRepository.save(user)).thenReturn(buildUser(true));
        UserModel userSaved = userService.saveUser(user);
        assertThat(userSaved.getUserId()).isNotNull();
        assertThat(userSaved.getUsername()).isEqualTo("Ismadrade");
        assertThat(userSaved.getFullName()).isEqualTo("Ismael Andrade");
        assertThat(userSaved.getEmail()).isEqualTo("ismadrade@gmail.com");
    }

    @Test
    @DisplayName("Test find an user by id")
    public void findAnUserById(){
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(buildUser(true)));
        Optional<UserModel> userModelOptional = userService.findById(UUID.fromString("9bbc8b99-7057-475b-81ca-6130e15bf030"));
        assertThat(userModelOptional).isNotEmpty();
        assertThat(userModelOptional.get().getUsername()).isEqualTo("Ismadrade");
        assertThat(userModelOptional.get().getFullName()).isEqualTo("Ismael Andrade");
        assertThat(userModelOptional.get().getEmail()).isEqualTo("ismadrade@gmail.com");
    }


    private UserModel buildUser(Boolean isSaved){
        UserModel userModel = UserModel.builder()
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
        if(isSaved)
            userModel.setUserId(UUID.fromString("166404d8-9d86-11ec-b909-0242ac120002"));
        return userModel;

    }
}
