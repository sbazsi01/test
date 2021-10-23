package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ProductLinesDto;
import hu.uni.eku.tzs.controller.dto.ProductLinesMapper;
import hu.uni.eku.tzs.model.ProductLines;
import hu.uni.eku.tzs.service.ProductLinesManager;
import hu.uni.eku.tzs.service.exceptions.ProductLinesAlreadyExistsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "ProductLines")
@RequestMapping("/productLines")
@RestController
@RequiredArgsConstructor
public class ProductLinesController {

    private final ProductLinesManager productLinesManager;

    private final ProductLinesMapper productLinesMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<ProductLinesDto> readAllProductLines() {
        return productLinesManager.readAll()
                .stream()
                .map(productLinesMapper::productLines2productLinesDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public ProductLinesDto create(@Valid @RequestBody ProductLinesDto recordRequestDto) {
        ProductLines productLines = productLinesMapper.productLinesDto2productLines(recordRequestDto);
        try {
            ProductLines recordedProductLines = productLinesManager.record(productLines);
            return productLinesMapper.productLines2productLinesDto(recordedProductLines);
        } catch (ProductLinesAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public ProductLinesDto update(@Valid @RequestBody ProductLinesDto updateRequestDto) {
        ProductLines productLines = productLinesMapper.productLinesDto2productLines(updateRequestDto);
        ProductLines updatedProductLines = productLinesManager.modify(productLines);
        return productLinesMapper.productLines2productLinesDto(updatedProductLines);
    }

}
