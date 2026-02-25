package com.rewow.testrewow.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordRequest {

    @NotNull(message = "petId es obligatorio")
    private Long petId;

    @Valid
    @Builder.Default
    private List<VaccinationDto> vaccinations = List.of();
}
