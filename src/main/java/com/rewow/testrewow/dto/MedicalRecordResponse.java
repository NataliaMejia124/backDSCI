package com.rewow.testrewow.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordResponse {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Datos de la mascota y dueño (para la vista de Medical Record)
    private Long petId;
    private String petOwnerName;
    private String petName;
    private String petType;   // Cat, Dog, Fish, Other
    private String petSize;   // Small, Medium, Big
    private String description;
    private List<VaccinationDto> vaccinations;
}
