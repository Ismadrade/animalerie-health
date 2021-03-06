package br.com.ismadrade.petmanagement.services.impl;

import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.repositories.PetRepository;
import br.com.ismadrade.petmanagement.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    @Override
    public PetModel savePet(PetModel petModel) {
        petModel.setCreationDate(LocalDateTime.now());
        petModel.setLastUpdateDate(LocalDateTime.now());
        return petRepository.save(petModel);
    }

    @Override
    public Boolean existRga(String rga) {
        return petRepository.existsByRga(rga);
    }

    @Override
    public PetModel updatePet(PetModel petModel) {
        petModel.setLastUpdateDate(LocalDateTime.now());
        return petRepository.save(petModel);
    }

    @Override
    public Optional<PetModel> findById(UUID petId) {
        return petRepository.findById(petId);
    }

    @Override
    public Page<PetModel> findAll(Specification<PetModel> spec, Pageable pageable) {
        return petRepository.findAll(spec, pageable);
    }

    @Override
    public void delete(PetModel petModel) {
        petRepository.delete(petModel);
    }
}
