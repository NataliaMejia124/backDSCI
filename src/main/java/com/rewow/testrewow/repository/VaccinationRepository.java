package com.rewow.testrewow.repository;

import com.rewow.testrewow.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    List<Vaccination> findByMedicalRecordId(Long medicalRecordId);
}
