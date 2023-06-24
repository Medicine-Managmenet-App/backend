package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.zaprogramujzycie.mma.dto.request.FamilyMemberRequest;
import pl.zaprogramujzycie.mma.dto.response.FamilyMemberResponse;
import pl.zaprogramujzycie.mma.entities.FamilyMember;


@Mapper(componentModel = "familyMember")
public interface FamilyMemberMapper {

    @Mapping(target = "familyId", source = "family.id")
    @Mapping(target = "prescriptionId", source = "prescription.id")
    FamilyMemberResponse mapToResponse (FamilyMember family);

    @Mapping(target = "family.id", source = "familyId")
    @Mapping(target = "prescription.id", source = "prescriptionId")
    FamilyMember mapToEntity (FamilyMemberRequest request);

}
