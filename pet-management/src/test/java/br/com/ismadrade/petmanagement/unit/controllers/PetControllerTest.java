package br.com.ismadrade.petmanagement.unit.controllers;

import br.com.ismadrade.petmanagement.dtos.PetDto;
import br.com.ismadrade.petmanagement.enums.PetGender;
import br.com.ismadrade.petmanagement.enums.UserStatus;
import br.com.ismadrade.petmanagement.enums.UserType;
import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.models.TypeModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.services.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@WebAppConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class PetControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @BeforeEach
    private void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Save a Pet")
    void savePet() throws Exception {
        PetDto petDto = buildPetDto();
        PetModel petSaved = buildPet(true);
        BDDMockito.given(petService.savePet(Mockito.any(PetModel.class))).willReturn(petSaved);
        String json = new ObjectMapper().writeValueAsString(petDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(petDto.getName()))
                .andExpect(jsonPath("petId").isNotEmpty());


    }

    private PetDto buildPetDto() {
        return PetDto
                .builder()
                .name("Laika")
                .birthday(LocalDate.of(2021, 9, 9))
                .rga("12345698")
                .type(UUID.fromString("ede14557-e644-4af1-944e-d2a04f67b687"))
                .user(UUID.fromString("234dcce4-4944-44da-bc8f-6323c7c3495f"))
                .build();
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
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.USER)
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
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
