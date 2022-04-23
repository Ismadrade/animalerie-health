package br.com.ismadrade.petmanagement.services;

import br.com.ismadrade.petmanagement.models.TypeModel;

import java.util.Optional;
import java.util.UUID;

public interface TypeService {

    TypeModel findById(UUID typeId);
}
