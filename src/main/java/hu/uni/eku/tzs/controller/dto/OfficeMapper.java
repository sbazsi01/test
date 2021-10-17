package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Office;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfficeMapper {

    OfficeDto office2officeDto(Office office);

    Office officeDto2Office(OfficeDto dto);
}
