package br.com.ismadrade.authuser.services;

import br.com.ismadrade.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserModel saveUser(UserModel userModel);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    Optional<UserModel> findById(UUID userId);

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
}
