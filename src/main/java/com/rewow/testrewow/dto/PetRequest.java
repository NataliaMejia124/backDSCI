package com.rewow.testrewow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetRequest {

    @NotNull(message = "ownerId es obligatorio")
    private Long ownerId;

    @NotBlank(message = "name es obligatorio")
    private String name;

    @NotBlank(message = "type es obligatorio")
    private String type;  // Cat, Dog, Fish, Other

    private String size;  // Small, Medium, Big

    private String description;
}
