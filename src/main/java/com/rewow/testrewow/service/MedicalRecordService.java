package com.rewow.testrewow.service;

import com.rewow.testrewow.dto.MedicalRecordRequest;
import com.rewow.testrewow.dto.MedicalRecordResponse;
import com.rewow.testrewow.dto.MedicalRecordUpdateRequest;
import com.rewow.testrewow.dto.VaccinationDto;
import com.rewow.testrewow.entity.MedicalRecord;
import com.rewow.testrewow.entity.Pet;
import com.rewow.testrewow.entity.Vaccination;
import com.rewow.testrewow.exception.ResourceNotFoundException;
import com.rewow.testrewow.repository.MedicalRecordRepository;
import com.rewow.testrewow.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PetRepository petRepository;

    @Transactional
    public MedicalRecordResponse create(MedicalRecordRequest request) {
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet no encontrado con id: " + request.getPetId()));

        if (medicalRecordRepository.findByPetId(pet.getId()).isPresent()) {
            throw new IllegalArgumentException("La mascota con id " + pet.getId() + " ya tiene un historial médico");
        }

        MedicalRecord record = MedicalRecord.builder()
                .pet(pet)
                .build();
        final MedicalRecord recordRef = record;

        List<Vaccination> vaccinations = request.getVaccinations().stream()
                .map(dto -> toVaccinationEntity(dto, recordRef))
                .collect(Collectors.toList());
        record.getVaccinations().clear();
        record.getVaccinations().addAll(vaccinations);

        record = medicalRecordRepository.save(record);
        return toResponse(record);
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordResponse> findAll() {
        return medicalRecordRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MedicalRecordResponse findById(Long id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial médico no encontrado con id: " + id));
        return toResponse(record);
    }

    @Transactional
    public MedicalRecordResponse update(Long id, MedicalRecordUpdateRequest request) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial médico no encontrado con id: " + id));
        final MedicalRecord recordRef = record;

        record.getVaccinations().clear();
        List<Vaccination> vaccinations = request.getVaccinations().stream()
                .map(dto -> toVaccinationEntity(dto, recordRef))
                .collect(Collectors.toList());
        record.getVaccinations().addAll(vaccinations);

        record = medicalRecordRepository.save(record);
        return toResponse(record);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Historial médico no encontrado con id: " + id);
        }
        medicalRecordRepository.deleteById(id);
    }

    private Vaccination toVaccinationEntity(VaccinationDto dto, MedicalRecord medicalRecord) {
        return Vaccination.builder()
                .numberVaccine(dto.getNumberVaccine())
                .type(dto.getType())
                .dateVaccine(dto.getDateVaccine())
                .medicalRecord(medicalRecord)
                .build();
    }

    private MedicalRecordResponse toResponse(MedicalRecord record) {
        Pet pet = record.getPet();
        List<VaccinationDto> vaccinationDtos = record.getVaccinations().stream()
                .map(v -> VaccinationDto.builder()
                        .id(v.getId())
                        .numberVaccine(v.getNumberVaccine())
                        .type(v.getType())
                        .dateVaccine(v.getDateVaccine())
                        .build())
                .collect(Collectors.toList());

        return MedicalRecordResponse.builder()
                .id(record.getId())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .petId(pet.getId())
                .petOwnerName(pet.getOwner().getName())
                .petName(pet.getName())
                .petType(pet.getType())
                .petSize(pet.getSize())
                .description(pet.getDescription())
                .vaccinations(vaccinationDtos)
                .build();
    }
}
