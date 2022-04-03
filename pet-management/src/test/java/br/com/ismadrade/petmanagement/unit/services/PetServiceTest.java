package br.com.ismadrade.petmanagement.unit.services;

import br.com.ismadrade.petmanagement.enums.PetGender;
import br.com.ismadrade.petmanagement.enums.UserStatus;
import br.com.ismadrade.petmanagement.enums.UserType;
import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.models.TypeModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.repositories.PetRepository;
import br.com.ismadrade.petmanagement.services.PetService;
import br.com.ismadrade.petmanagement.services.impl.PetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class PetServiceTest {

    private PetService petService;

    @MockBean
    private PetRepository petRepository;

    @BeforeEach
    void setup(){
        this.petService = new PetServiceImpl(petRepository);
    }

    @Test
    @DisplayName("Test save a pet")
    void savePet(){
        PetModel petModel = buildPet(false);
        Mockito.when(petRepository.save(petModel)).thenReturn(buildPet(true));
        PetModel petSaved = petService.savePet(petModel);
        assertThat(petSaved.getPetId()).isNotNull();
        assertThat(petSaved.getName()).isEqualTo("Laika");
        assertThat(petSaved.getGender()).isEqualTo(PetGender.FEMALE);
    }


    private PetModel buildPet(Boolean isSaved) {

        TypeModel type = TypeModel.builder()
                .name("Cachorro")
                .breed("Husky")
                .typeId(UUID.fromString("ede14557-e644-4af1-944e-d2a04f67b687"))
                .observation("A happy Husky")
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();

        UserModel user = UserModel
                .builder()
                .userId(UUID.fromString("234dcce4-4944-44da-bc8f-6323c7c3495f"))
                .fullName("Ismael da Silva de Andrade")
                .cpf("01478796547")
                .email("user@user.com")
                .userStatus(UserStatus.ACTIVE.toString())
                .userType(UserType.USER.toString())
                .build();

        PetModel pet = PetModel
                .builder()
                .name("Laika")
                .birthday(LocalDate.of(2020, 9, 9))
                .creationDate(LocalDateTime.now())
                .gender(PetGender.FEMALE)
                .lastUpdateDate(LocalDateTime.now())
                .rga("123456789")
                .user(user)
                .type(type)
                .build();

        if(isSaved)
            pet.setPetId(UUID.fromString("d7aef0c7-d4d9-4ea5-a8e1-2b53253e8c79"));
        return pet;
    }

}
