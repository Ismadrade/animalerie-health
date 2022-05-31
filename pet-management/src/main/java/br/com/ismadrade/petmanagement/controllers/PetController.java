package br.com.ismadrade.petmanagement.controllers;

import br.com.ismadrade.petmanagement.configs.security.AuthenticationCurrentUserService;
import br.com.ismadrade.petmanagement.dtos.PetDto;
import br.com.ismadrade.petmanagement.exceptions.CustomException;
import br.com.ismadrade.petmanagement.mappers.Mapper;
import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.models.TypeModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.services.PetService;
import br.com.ismadrade.petmanagement.services.TypeService;
import br.com.ismadrade.petmanagement.services.UserService;
import br.com.ismadrade.petmanagement.views.PetView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;
    private final Mapper<PetDto, PetModel> mapper;


    @PostMapping
    public ResponseEntity<?> petRegister(@RequestBody
                                         @JsonView(PetView.RegistrationPost.class)
                                         @Validated(PetView.RegistrationPost.class) PetDto petDto){

        log.debug("POST petRegister petDto received {}", petDto.toString());

        if(petService.existRga(petDto.getRga()))
            throw new CustomException(HttpStatus.CONFLICT, "Pet já cadastrado para o RGA informado!");

        PetModel petModel = mapper.toEntity(petDto);
        petModel.setUser(authenticationCurrentUserService.getCurrentUser());
        this.petService.savePet(petModel);

        log.debug("POST petRegister petId {}", petModel.getPetId());
        log.info("Pet has been saved! petId {}", petModel.getPetId());

        return ResponseEntity.status(HttpStatus.CREATED).body(petModel);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetModel> findById(@PathVariable(value = "petId") UUID petId){

        log.debug("GET findById received idPet {}", petId);
        PetModel petModel = petService.findById(petId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Pet não encontrado para o id informado"));
        log.info("GET findById founded!");

        return ResponseEntity.status(HttpStatus.OK).body(petModel);

    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetModel> editPet(@PathVariable(value = "petId") UUID petId,
                                            @RequestBody
                                            @JsonView(PetView.RegistrationPost.class)
                                            @Validated(PetView.RegistrationPost.class) PetDto petDto){
        log.debug("PUT editPet petDto received {}", petDto.toString());

        var lastPet = petService.findById(petId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Pet não encontrado para o id informado"));
        var petModel = mapper.toEntity(petDto);
        petModel.setPetId(lastPet.getPetId());
        petModel.setCreationDate(lastPet.getCreationDate());

        BeanUtils.copyProperties(petModel,lastPet);
        petModel.setLastUpdateDate(LocalDateTime.now());
        petService.updatePet(petModel);

        log.debug("PUT editPet petId {}", petModel.getPetId());
        log.info("Pet has been edited! petId {}", petModel.getPetId());

        return ResponseEntity.status(HttpStatus.OK).body(petModel);

    }


}
