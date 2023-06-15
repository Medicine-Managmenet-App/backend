package pl.zaprogramujzycie.mma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entity.Prescription;

public interface PrescribedMedicinesRepository extends JpaRepository<Prescription, Long> { }
