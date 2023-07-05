package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import pl.zaprogramujzycie.mma.dto.response.FamilyResponse;
import pl.zaprogramujzycie.mma.entities.*;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "family")
public interface FamilyMapper {


    default FamilyResponse mapToResponse (Family family) {
        List<Long> membersIds = new ArrayList<>();
        List<FamilyMember> members = family.getMembers();
        for (FamilyMember fM : members) {
            membersIds.add(fM.getId());
        }
        List<String> logins = family.getLogins();

        List<Long> medicinesIds = new ArrayList<>();
        List<Medicine> medicines = family.getMedicines();
        for (Medicine m : medicines) {
            medicinesIds.add(m.getId());
        }
        return new FamilyResponse(family.getId(), membersIds, logins, medicinesIds);
    }
}
