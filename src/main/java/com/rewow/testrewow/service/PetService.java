package com.rewow.testrewow.service;

import com.rewow.testrewow.dto.PetRequest;
import com.rewow.testrewow.dto.PetResponse;
import com.rewow.testrewow.entity.Owner;
import com.rewow.testrewow.entity.Pet;
import com.rewow.testrewow.exception.ResourceNotFoundException;
import com.rewow.testrewow.repository.OwnerRepository;
import com.rewow.testrewow.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    @Transactional
    public PetResponse create(PetRequest request) {
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner no encontrado con id: " + request.getOwnerId()));

        Pet pet = Pet.builder()
                .name(request.getName())
                .type(request.getType())
                .size(request.getSize())
                .description(request.getDescription())
                .owner(owner)
                .build();

        pet = petRepository.save(pet);
        return toResponse(pet);
    }

    private PetResponse toResponse(Pet pet) {
        return PetResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .type(pet.getType())
                .size(pet.getSize())
                .description(pet.getDescription())
                .ownerId(pet.getOwner().getId())
                .ownerName(pet.getOwner().getName())
                .build();
    }
}
