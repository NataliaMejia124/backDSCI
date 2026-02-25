package com.rewow.testrewow.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetResponse {

    private Long id;
    private String name;
    private String type;
    private String size;
    private String description;
    private Long ownerId;
    private String ownerName;
}
