package com.rewow.testrewow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "VACCINATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaccination_seq")
    @SequenceGenerator(name = "vaccination_seq", sequenceName = "vaccination_seq", allocationSize = 1)
    private Long id;

    @Column(name = "number_vaccine", nullable = false)
    private Integer numberVaccine;

    @Column(name = "type", length = 100, nullable = false)
    private String type;

    @Column(name = "date_vaccine", nullable = false)
    private LocalDate dateVaccine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_record_id", nullable = false)
    private MedicalRecord medicalRecord;
}
