package br.com.ismadrade.petmanagement.services;

import br.com.ismadrade.petmanagement.models.PetModel;

public interface PetService {

    PetModel savePet(PetModel petModel);
    Boolean existRga(String rga);
}
