package br.com.ismadrade.petmanagement.mappers.impl;

import br.com.ismadrade.petmanagement.dtos.PetDto;
import br.com.ismadrade.petmanagement.exceptions.CustomException;
import br.com.ismadrade.petmanagement.mappers.Mapper;
import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.models.TypeModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.repositories.TypeRepository;
import br.com.ismadrade.petmanagement.repositories.UserRepository;
import br.com.ismadrade.petmanagement.services.TypeService;
import br.com.ismadrade.petmanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class PetMapper implements Mapper<PetDto, PetModel> {

    private final ModelMapper mapper;
    private final UserService userService;
    private final TypeService typeService;

    public PetModel toEntity(PetDto dto) {
        PetModel petModel = mapper.map(dto, PetModel.class);
        petModel.setUser(userService.findById(dto.getUserId()));
        petModel.setType(typeService.findById(dto.getTypeId()));
        return petModel;
    }

}
