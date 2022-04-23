package br.com.ismadrade.petmanagement.services;

import br.com.ismadrade.petmanagement.models.PetModel;

import java.util.Optional;
import java.util.UUID;

public interface PetService {

    PetModel savePet(PetModel petModel);
    Boolean existRga(String rga);
    PetModel updatePet(PetModel petModel);
    Optional<PetModel> findById(UUID petId);
}
