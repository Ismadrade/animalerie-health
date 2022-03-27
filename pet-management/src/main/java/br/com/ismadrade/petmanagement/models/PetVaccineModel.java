package br.com.ismadrade.petmanagement.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_PETS_VACCINES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetVaccineModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", name = "pet_vaccine_id")
    private UUID petVaccineId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id")
    private PetModel pet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vaccine_id")
    private VaccineModel vaccine;
}
