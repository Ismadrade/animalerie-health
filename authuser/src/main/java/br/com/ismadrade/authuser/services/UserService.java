package br.com.ismadrade.authuser.services;

import br.com.ismadrade.authuser.models.UserModel;

public interface UserService {
    UserModel saveUser(UserModel userModel);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
