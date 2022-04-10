package br.com.ismadrade.petmanagement.services;

import br.com.ismadrade.petmanagement.models.TypeModel;

import java.util.Optional;
import java.util.UUID;

public interface TypeService {

    Optional<TypeModel> findById(UUID typeId);
}
