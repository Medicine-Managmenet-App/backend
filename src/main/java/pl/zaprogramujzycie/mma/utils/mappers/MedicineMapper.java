package pl.zaprogramujzycie.mma.utils.mappers;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import pl.zaprogramujzycie.mma.dto.request.MedicineRequest;
import pl.zaprogramujzycie.mma.dto.response.MedicineResponse;
import pl.zaprogramujzycie.mma.entities.Medicine;

import java.util.List;


@Mapper(componentModel = "medicine")
public interface MedicineMapper {
    MedicineResponse mapToResponse (Medicine medicine);
    Medicine mapToEntity (MedicineRequest request);

    List<MedicineResponse> mapToList (Page<Medicine> page);

}
