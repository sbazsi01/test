package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.ProductLines;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductLinesMapper {

    ProductLinesDto productLines2productLinesDto(ProductLines productLines);

    ProductLines productLinesDto2productLines(ProductLinesDto dto);
}
