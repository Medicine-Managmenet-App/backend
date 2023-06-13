package pl.zaprogramujzycie.mma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zaprogramujzycie.mma.entity.PrescribedMedicines;

public interface PrescribedMedicinesRepository extends JpaRepository<PrescribedMedicines, Long> { }
