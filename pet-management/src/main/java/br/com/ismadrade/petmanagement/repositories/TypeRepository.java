package br.com.ismadrade.petmanagement.repositories;

import br.com.ismadrade.petmanagement.models.TypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TypeRepository extends JpaRepository<TypeModel, UUID> {
}
