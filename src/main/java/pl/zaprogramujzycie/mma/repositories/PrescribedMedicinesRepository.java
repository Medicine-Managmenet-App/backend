package pl.zaprogramujzycie.mma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entities.Prescription;

public interface PrescribedMedicinesRepository extends JpaRepository<Prescription, Long> { }
