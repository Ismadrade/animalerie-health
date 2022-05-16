package br.com.ismadrade.petmanagement.models;

import br.com.ismadrade.petmanagement.enums.UserStatus;
import br.com.ismadrade.petmanagement.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends RepresentationModel<UserModel> implements Serializable {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID userId;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(length = 20)
    private String cpf;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false, name = "status")
    private String userStatus;

    @Column(nullable = false, name = "type")
    private String userType;

}
