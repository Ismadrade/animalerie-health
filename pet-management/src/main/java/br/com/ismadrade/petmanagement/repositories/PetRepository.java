package br.com.ismadrade.petmanagement.repositories;

import br.com.ismadrade.petmanagement.models.PetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<PetModel, UUID> {
    Boolean findByRga(String rga);
}
