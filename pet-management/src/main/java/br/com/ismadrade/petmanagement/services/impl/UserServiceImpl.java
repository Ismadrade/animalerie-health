package br.com.ismadrade.petmanagement.services.impl;

import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.repositories.UserRepository;
import br.com.ismadrade.petmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
