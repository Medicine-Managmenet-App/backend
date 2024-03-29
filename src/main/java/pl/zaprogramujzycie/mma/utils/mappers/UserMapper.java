package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.zaprogramujzycie.mma.dto.request.UserRequest;
import pl.zaprogramujzycie.mma.dto.response.UserResponse;
import pl.zaprogramujzycie.mma.entities.User;


@Mapper(componentModel = "user")
public interface UserMapper {
    @Mapping(target="family", source="familyId")
    UserResponse mapToResponse (final User user);

    User mapToEntity (final UserRequest request);

    @Mapping(target="family.id", source="family")
    User mapResponseToEntity (final UserResponse response);
}
