package com.rewow.testrewow.dto;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordUpdateRequest {

    @Valid
    @Builder.Default
    private List<VaccinationDto> vaccinations = List.of();
}
