package com.rewow.testrewow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationDto {

    private Long id;

    @NotNull(message = "numberVaccine es obligatorio")
    private Integer numberVaccine;

    @NotBlank(message = "type es obligatorio")
    private String type;

    @NotNull(message = "dateVaccine es obligatorio")
    private LocalDate dateVaccine;
}
