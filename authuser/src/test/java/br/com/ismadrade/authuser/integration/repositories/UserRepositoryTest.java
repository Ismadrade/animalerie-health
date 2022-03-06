package br.com.ismadrade.authuser.integration.repositories;


import br.com.ismadrade.authuser.enums.UserStatus;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application.yml")
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Test save an user.")
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
}
