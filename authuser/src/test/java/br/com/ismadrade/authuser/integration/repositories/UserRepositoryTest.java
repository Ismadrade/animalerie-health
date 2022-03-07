package br.com.ismadrade.authuser.integration.repositories;


import br.com.ismadrade.authuser.enums.UserStatus;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application.yml")
@Sql("/user_service.sql")
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Save an user.")
    void saveAnUser(){
        UserModel user = UserModel.builder()
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

        UserModel userSaved = userRepository.save(user);
        assertNotNull(userSaved);
        assertNotNull(userSaved.getUserId());

    }

    @Test
    @DisplayName("Find an user by id.")
    void findAnUserById(){

        Optional<UserModel> userOptional = userRepository.findById(UUID.fromString("9bbc8b99-7057-475b-81ca-6130e15bf030"));
        assertThat(userOptional).isNotEmpty();
        assertEquals(userOptional.get().getEmail(), "rafys2000@gmail.com");

    }
}
