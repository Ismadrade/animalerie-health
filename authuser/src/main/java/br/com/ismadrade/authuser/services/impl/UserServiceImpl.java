package br.com.ismadrade.authuser.services.impl;

import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.repositories.UserRepository;
import br.com.ismadrade.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }


    @Override
    public UserModel saveUser(UserModel userModel) {
        return this.userRepository.save(userModel);
    }
}
