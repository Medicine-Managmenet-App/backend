package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.zaprogramujzycie.mma.dto.request.FamilyRequest;
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

    // default Family mapToEntity (FamilyRequest request) {
    //     Family family = new Family();
    //
    //     // List<Long> membersIds = request.members();
    //     // List<Long> usersIds = request.users();
    //     // List<Long> medicinesIds = request.medicines();
    //
    //     List<FamilyMember> members = new ArrayList<>();
    //     List<User> users = new ArrayList<>();
    //     List<Medicine> medicines = new ArrayList<>();
    //
    //     for(long id : membersIds) {
    //         members.add(mapToMember(id));
    //     }
    //     for(long id : usersIds) {
    //         users.add(mapToUser(id));
    //     }
    //     for(long id : medicinesIds) {
    //         medicines.add(mapToMedicine(id));
    //     }
    //
    //     return new Family(null, members, users, medicines);
    // }

    default Family mapResponseToEntity (FamilyResponse request) {

        List<Long> membersIds = request.members();
        List<String> usersIds = request.users();
        List<Long> medicinesIds = request.medicines();

        List<FamilyMember> members = new ArrayList<>();
        List<Medicine> medicines = new ArrayList<>();

        for(long id : membersIds) {
            members.add(mapToMember(id));
        }

        for(long id : medicinesIds) {
            medicines.add(mapToMedicine(id));
        }

        return new Family(request.id(), members, usersIds, medicines);
    }
    @Mapping(target = "id", source = "id")
    FamilyMember mapToMember (Long id);
    @Mapping(target = "id", source = "id")
    User mapToUser (Long id);
    @Mapping(target = "id", source = "id")
    Medicine mapToMedicine (Long id);
}
