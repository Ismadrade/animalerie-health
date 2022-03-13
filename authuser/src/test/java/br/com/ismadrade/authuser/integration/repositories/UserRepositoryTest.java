package br.com.ismadrade.authuser.integration.repositories;


import br.com.ismadrade.authuser.enums.UserStatus;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                .email("ismael.andrade@gmail.com")
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

    @Test
    @DisplayName("Find all user")
    void findAllUser(){
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC,"userId"));
        Specification spec =  (root, query, cb) ->  null;
        Page result = userRepository.findAll(spec, pageable);
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(4);
    }

    @Test
    @DisplayName("Find all user by spec id and status")
    void findAllUserByIdAndStatus(){
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC,"userId"));
        Specification spec =  (root, query, cb) -> {
            query.distinct(true);
            Root<UserModel> user = root;
            return cb.and(cb.equal(user.get("userId"), UUID.fromString("d1ce0dbe-1cdc-4305-8798-3a3fa29f950f")), cb.equal(user.get("userStatus"), UserStatus.BLOCKED));
        };
        Page<UserModel> result = userRepository.findAll(spec, pageable);
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertEquals(result.getContent().get(0).getUserId(), UUID.fromString("d1ce0dbe-1cdc-4305-8798-3a3fa29f950f"));
        assertEquals(result.getContent().get(0).getUserStatus(), UserStatus.BLOCKED);
    }
}
