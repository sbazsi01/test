package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.OfficeDto;
import hu.uni.eku.tzs.controller.dto.OfficeMapper;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.OfficeManager;
import hu.uni.eku.tzs.service.exceptions.OfficeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OfficeNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "Offices")
@RequestMapping("/offices")
@RestController
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeManager officeManager;

    private final OfficeMapper officeMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<OfficeDto> readAllOffices() {
        return officeManager.readAllOffices()
            .stream()
            .map(officeMapper::office2officeDto)
            .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public OfficeDto create(@Valid @RequestBody OfficeDto recordRequestDto) {
        Office office = officeMapper.officeDto2Office(recordRequestDto);
        try {
            Office recordedOffice = officeManager.record(office);
            return officeMapper.office2officeDto(recordedOffice);
        } catch (OfficeAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public OfficeDto update(@Valid @RequestBody OfficeDto updateRequestDto) {
        Office office = officeMapper.officeDto2Office(updateRequestDto);
        Office updatedOffice = officeManager.modify(office);
        return officeMapper.office2officeDto(updatedOffice);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam String officeCode) {
        try {
            officeManager.delete(officeManager.readByOfficeCode(officeCode));
        } catch (OfficeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/{officeCode}"})
    public void deleteBasedOnPath(@PathVariable String officeCode) {
        this.delete(officeCode);
    }

}
