package br.com.ismadrade.petmanagement.services;

import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface PetService {

    PetModel savePet(PetModel petModel);
    Boolean existRga(String rga);
    PetModel updatePet(PetModel petModel);
    Optional<PetModel> findById(UUID petId);

    Page<PetModel> findAll(Specification<PetModel> spec, Pageable pageable);
}
