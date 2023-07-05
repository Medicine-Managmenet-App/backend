package pl.zaprogramujzycie.mma.utils.mappers;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import pl.zaprogramujzycie.mma.dto.response.*;
import pl.zaprogramujzycie.mma.entities.FamilyMember;
import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "familyMember")
public interface FamilyMemberMapper {

    default FamilyMemberResponse mapToResponse(final FamilyMember family) {
        return new FamilyMemberResponse(family.getId(), family.getName(), false);
    }

    default FamilyMemberChildResponse mapToChildResponse(final FamilyMember child) {
        return new FamilyMemberChildResponse(child.getId(), child.getName(), true, child.getAge(), child.getWeight());
    }

    default FamilyMembersResponse mapToList(final Page<FamilyMember> page) {
        List<FamilyMemberResponse> members = new ArrayList<>();
        for (FamilyMember member : page) {
            members.add(mapToResponse(member));
        }
        return new FamilyMembersResponse(members);
    }
}
