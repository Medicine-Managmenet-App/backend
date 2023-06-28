package pl.zaprogramujzycie.mma.services;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.request.PrescriptionRequest;
import pl.zaprogramujzycie.mma.dto.response.PrescribedMedicineResponse;
import pl.zaprogramujzycie.mma.dto.response.PrescriptionResponse;
import pl.zaprogramujzycie.mma.entities.PrescribedMedicine;
import pl.zaprogramujzycie.mma.entities.Prescription;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.PrescriptionRepository;
import pl.zaprogramujzycie.mma.utils.UserToFamilyValidator;
import pl.zaprogramujzycie.mma.utils.mappers.PrescribedMedicineMapper;
import pl.zaprogramujzycie.mma.utils.mappers.PrescriptionMapper;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PrescriptionService {

    private final PrescriptionRepository repository;
    private final UserToFamilyValidator validator;

    private final PrescribedMedicineService prescribedMedicineService;

    private final PrescriptionMapper mapper
            = Mappers.getMapper(PrescriptionMapper.class);

    private final PrescribedMedicineMapper prescribedMedicineMapper
            = Mappers.getMapper(PrescribedMedicineMapper.class);


    PrescriptionService(PrescriptionRepository repository, UserToFamilyValidator validator, PrescribedMedicineService prescribedMedicineService) {
        this.repository = repository;
        this.validator = validator;
        this.prescribedMedicineService = prescribedMedicineService;
    }

    @Transactional
    public PrescriptionResponse save(final PrescriptionRequest request, final Principal principal,
                                     final long familyMemberId, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Prescription prescription = mapper.mapToEntity(request);
        prescription.setFamilyMemberId(familyMemberId);
        return mapper.mapToResponse(repository.save(prescription));
    }


//ToDO change to familyMemberId
    public PrescriptionResponse findById(final Principal principal, final long id, final long familyMemberId,
                                         final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Prescription response = findEntity(id, familyMemberId);
        return mapper.mapToResponse(response);
    }

    //ToDO take care of principal
    private Prescription findEntity(final long id, final long familyMemberId) throws NotFoundException {
        Optional<Prescription> response = repository.findByIdAndFamilyMemberId(id, familyMemberId);
        return response.orElseThrow(NotFoundException:: new);
    }
    @Transactional
    public void partialUpdate(final long id, final PrescriptionRequest request,
                              final Principal principal, final long familyMemberId,
                              final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Prescription updatedEntity = findEntity(id, familyMemberId);
        if (request.prescribedMedicines() != null) {
            updatedEntity.setPrescribedMedicines(getMatchingPrescribedMedicine(request.prescribedMedicines(), id, familyId, principal));
            repository.save(updatedEntity);
        }
    }

    private List<PrescribedMedicine> getMatchingPrescribedMedicine (final List<Long> ids, final long id,
                                                              final long familyId, final Principal principal) throws NotFoundException {
        List<PrescribedMedicineResponse> list = new ArrayList<>();
        for (long medId : ids) {
            list.add(prescribedMedicineService.findById(medId, id, familyId, principal));
        }
        return prescribedMedicineMapper.mapToEntityList(list);
    }
    @Transactional
    public void deleteById(final long id, final Principal principal,
                           final long familyMemberId, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Prescription response = findEntity(id, familyMemberId);
        repository.deleteById(response.getId());
    }
}
