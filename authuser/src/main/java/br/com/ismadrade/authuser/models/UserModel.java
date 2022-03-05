package br.com.ismadrade.authuser.models;

import br.com.ismadrade.authuser.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_USERS")
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String password;
    @Column(length = 20)
    private String cpf;
    @Column(length = 20)
    private String phoneNumber;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    public void setFullName(String fullName) {
        this.fullName = Stream.of(fullName.trim().split(" "))
                .map(word -> StringUtils.capitalize(word.toLowerCase()))
                .collect(Collectors.joining(" "));
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
}
