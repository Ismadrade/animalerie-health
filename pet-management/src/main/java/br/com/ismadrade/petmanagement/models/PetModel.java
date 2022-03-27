package br.com.ismadrade.petmanagement.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_PETS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", name = "pet_id")
    private UUID petId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(nullable = false, length = 20)
    private String rga;

    @ManyToOne
    @JoinColumn(nullable = false, name = "pet_type")
    @Enumerated(EnumType.STRING)
    private TypeModel type;

    @ManyToOne
    @JoinColumn(nullable = false, name = "authuser")
    @Enumerated(EnumType.STRING)
    private UserModel user;

    @Column(nullable = false, name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @Column(nullable = false, name = "last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private Set<PetVaccineModel> petVaccines;




}
