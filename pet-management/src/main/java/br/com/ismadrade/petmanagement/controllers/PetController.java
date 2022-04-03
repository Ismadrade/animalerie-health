package br.com.ismadrade.petmanagement.controllers;

import br.com.ismadrade.petmanagement.dtos.PetDto;
import br.com.ismadrade.petmanagement.exceptions.CustomException;
import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.services.PetService;
import br.com.ismadrade.petmanagement.views.PetView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<?> petRegister(@RequestBody
                                         @JsonView(PetView.RegistrationPost.class)
                                         @Validated(PetView.RegistrationPost.class) PetDto petDto){
        log.debug("POST petRegister petDto received {}", petDto.toString());

        if(petService.existRga(petDto.getRga())){
            log.warn("Pet já cadastrado para o RGA informado {} ", petDto.getRga());
            throw new CustomException(HttpStatus.CONFLICT, "Pet já cadastrado para o RGA informado!");
        }

        var petModel = new PetModel();
        BeanUtils.copyProperties(petDto, petModel);
        petModel.setCreationDate(LocalDateTime.now());
        petModel.setLastUpdateDate(LocalDateTime.now());
        petService.savePet(petModel);

        log.debug("POST petRegister petId {}", petModel.getPetId());
        log.info("Pet has been saved! petId {}", petModel.getPetId());

        return ResponseEntity.status(HttpStatus.CREATED).body(petModel);
    }


}
