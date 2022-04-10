package br.com.ismadrade.petmanagement.controllers;

import br.com.ismadrade.petmanagement.dtos.PetDto;
import br.com.ismadrade.petmanagement.exceptions.CustomException;
import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.models.TypeModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.services.PetService;
import br.com.ismadrade.petmanagement.services.TypeService;
import br.com.ismadrade.petmanagement.services.UserService;
import br.com.ismadrade.petmanagement.views.PetView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final UserService userService;
    private final TypeService typeService;

    @Autowired
    public PetController(PetService petService, UserService userService, TypeService typeService) {
        this.petService = petService;
        this.userService = userService;
        this.typeService = typeService;
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

        Optional<UserModel> optionalUserModel = userService.findById(petDto.getUser());
        if(optionalUserModel.isEmpty()){
            log.warn("Usuário não encontrado para este pet. user: {} ", petDto.getUser());
            throw new CustomException(HttpStatus.NOT_FOUND, "Usuário não encontrado para este pet!");
        }

        Optional<TypeModel> optionalTypeModel = typeService.findById(petDto.getType());
        if(optionalTypeModel.isEmpty()){
            log.warn("Tipo não encontrado para este pet. tipo: {} ", petDto.getType());
            throw new CustomException(HttpStatus.NOT_FOUND, "Tipo não encontrado para este pet!");
        }


        var petModel = new PetModel();
        BeanUtils.copyProperties(petDto, petModel);
        petModel.setCreationDate(LocalDateTime.now());
        petModel.setLastUpdateDate(LocalDateTime.now());
        petModel.setUser(optionalUserModel.get());
        petModel.setType(optionalTypeModel.get());
        petService.savePet(petModel);

        log.debug("POST petRegister petId {}", petModel.getPetId());
        log.info("Pet has been saved! petId {}", petModel.getPetId());

        return ResponseEntity.status(HttpStatus.CREATED).body(petModel);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetModel> findById(@PathVariable(value = "petId") UUID petId){
        log.debug("GET findById received idPet {}", petId);
        Optional<PetModel> optionalPetModel = petService.findById(petId);

        if(optionalPetModel.isEmpty())
            throw new CustomException(HttpStatus.NOT_FOUND, "Pet não encontrado para o id informado");

        log.info("GET findById founded!");
        return ResponseEntity.status(HttpStatus.OK).body(optionalPetModel.get());

    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetModel> editPet(@PathVariable(value = "petId") UUID petId,
                                            @RequestBody
                                            @JsonView(PetView.RegistrationPost.class)
                                            @Validated(PetView.RegistrationPost.class) PetDto dto){
        log.debug("PUT editPet petDto received {}", dto.toString());
        Optional<PetModel> optionalPetModel = petService.findById(petId);

        if(optionalPetModel.isEmpty())
            throw new CustomException(HttpStatus.NOT_FOUND, "Pet não encontrado para o id informado");

        Optional<UserModel> optionalUserModel = userService.findById(dto.getUser());
        if(optionalUserModel.isEmpty()){
            log.warn("Usuário não encontrado para este pet. user: {} ", dto.getUser());
            throw new CustomException(HttpStatus.NOT_FOUND, "Usuário não encontrado para este pet!");
        }

        Optional<TypeModel> optionalTypeModel = typeService.findById(dto.getType());
        if(optionalTypeModel.isEmpty()){
            log.warn("Tipo não encontrado para este pet. tipo: {} ", dto.getType());
            throw new CustomException(HttpStatus.NOT_FOUND, "Tipo não encontrado para este pet!");
        }

        var petModel = optionalPetModel.get();
        BeanUtils.copyProperties(dto, petModel);
        petModel.setPetId(petId);
        petModel.setUser(optionalUserModel.get());
        petModel.setType(optionalTypeModel.get());
        petModel.setLastUpdateDate(LocalDateTime.now());
        petService.savePet(petModel);

        log.debug("PUT editPet petId {}", petModel.getPetId());
        log.info("Pet has been edited! petId {}", petModel.getPetId());
        return ResponseEntity.status(HttpStatus.OK).body(petModel);

    }


}
