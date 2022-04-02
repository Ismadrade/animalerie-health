package br.com.ismadrade.petmanagement.dtos;

import br.com.ismadrade.petmanagement.enums.PetGender;
import br.com.ismadrade.petmanagement.models.TypeModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.views.PetView;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {

    private UUID petId;

    @Size(min = 2, max = 50, groups = PetView.RegistrationPost.class)
    @JsonView(PetView.RegistrationPost.class)
    private String name;

    @JsonView(PetView.RegistrationPost.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;

    @JsonView(PetView.RegistrationPost.class)
    private PetGender gender;

    @Size(max = 20, groups = PetView.RegistrationPost.class)
    @JsonView(PetView.RegistrationPost.class)
    private String rga;

    @JsonView(PetView.RegistrationPost.class)
    private UUID type;

    @JsonView(PetView.RegistrationPost.class)
    private UUID user;



}
