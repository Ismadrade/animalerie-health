package br.com.ismadrade.petmanagement.services.impl;

import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.repositories.PetRepository;
import br.com.ismadrade.petmanagement.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    private PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    @Override
    public PetModel savePet(PetModel petModel) {
        return petRepository.save(petModel);
    }
}
