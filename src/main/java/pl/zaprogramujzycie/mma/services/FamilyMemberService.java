package pl.zaprogramujzycie.mma.services;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zaprogramujzycie.mma.dto.request.FamilyMemberRequest;
import pl.zaprogramujzycie.mma.dto.response.*;
import pl.zaprogramujzycie.mma.entities.*;
import pl.zaprogramujzycie.mma.exceptions.NotFoundException;
import pl.zaprogramujzycie.mma.repositories.FamilyMemberRepository;
import pl.zaprogramujzycie.mma.utils.UserToFamilyValidator;
import pl.zaprogramujzycie.mma.utils.mappers.FamilyMemberMapper;
import java.security.Principal;
import java.util.Optional;


@Slf4j
@Service
public class FamilyMemberService {

    private final FamilyMemberRepository repository;
    private final UserToFamilyValidator validator;
    private final FamilyMemberMapper mapper = Mappers.getMapper(FamilyMemberMapper.class);

    public FamilyMemberService(FamilyMemberRepository repository, UserToFamilyValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional
    public FamilyMemberResponse save(final FamilyMemberRequest request, final Principal principal, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        int isChild = 0;
        if (request.isChild()) {isChild = 1;}
        FamilyMember familyMember = new FamilyMember(null, request.getName(),isChild , request.getAge(), request.getWeight(), familyId);
        return mapper.mapToResponse(repository.save(familyMember));
    }

    public FamilyMemberResponse findById(final Principal principal, final long id, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        FamilyMember response = findEntity(id, familyId);
        if (response.getIsChild() == 1) {
            return mapper.mapToChildResponse(response);
        } else {
            return mapper.mapToResponse(response);
        }
    }

    public FamilyMembersResponse findAll(final Principal principal, final Pageable pageable, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        Page<FamilyMember> members = repository.findByFamilyId(familyId, PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))));
        return mapper.mapToList(members);
    }

    @Transactional
    public void update(final long id, final FamilyMemberRequest request, final Principal principal, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        FamilyMember response = findEntity(id, familyId);
        int isChild = 0;
        if (request.isChild()) {isChild = 1;}
        response.setName(request.getName());
        response.setIsChild(isChild);
        response.setAge(request.getAge());
        response.setWeight(request.getWeight());
    }

    @Transactional
    public void deleteById(final long id, final Principal principal, final long familyId) throws NotFoundException {
        validator.checkUserPermissionsOnFamily(principal, familyId);
        FamilyMember response = findEntity(id, familyId);
        try {
            repository.deleteById(response.getId());
        } catch (DataIntegrityViolationException ic) {
            throw new DataIntegrityViolationException("Medicine connected to other object/objects");
        }
    }

    private FamilyMember findEntity(final long id, final long familyId) throws NotFoundException {
        Optional<FamilyMember> response = repository.findByIdAndFamilyId(id, familyId);
        return response.orElseThrow(NotFoundException:: new);
    }
}
