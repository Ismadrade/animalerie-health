package br.com.ismadrade.petmanagement.services.impl;

import br.com.ismadrade.petmanagement.models.TypeModel;
import br.com.ismadrade.petmanagement.repositories.TypeRepository;
import br.com.ismadrade.petmanagement.services.TypeService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Optional<TypeModel> findById(UUID typeId) {
        return this.typeRepository.findById(typeId);
    }
}
