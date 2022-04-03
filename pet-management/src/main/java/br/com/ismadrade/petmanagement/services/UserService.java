package br.com.ismadrade.petmanagement.services;

import br.com.ismadrade.petmanagement.models.UserModel;

import java.util.UUID;

public interface UserService {
    void save(UserModel userModel);
    void delete(UUID userId);
}
