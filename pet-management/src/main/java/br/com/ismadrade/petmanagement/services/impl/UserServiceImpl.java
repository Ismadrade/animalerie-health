package br.com.ismadrade.petmanagement.services.impl;

import br.com.ismadrade.petmanagement.exceptions.CustomException;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.repositories.UserRepository;
import br.com.ismadrade.petmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(UserModel userModel) {
        this.userRepository.save(userModel);
    }

    @Override
    public void delete(UUID userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public UserModel findById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Usuário não encontrado para o pet informado!"));
    }
}
