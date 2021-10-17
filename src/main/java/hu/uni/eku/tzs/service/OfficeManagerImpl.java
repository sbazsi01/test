package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.OfficeRepository;
import hu.uni.eku.tzs.dao.entity.OfficeEntity;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.exceptions.OfficeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OfficeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficeManagerImpl implements OfficeManager {

    private final OfficeRepository officeRepository;

    private static Office convertOfficeEntity2Model(OfficeEntity officeEntity) {
        return new Office(
                officeEntity.getOfficeCode(),
                officeEntity.getCity(),
                officeEntity.getPhone(),
                officeEntity.getAddressLine1(),
                officeEntity.getAddressLine2(),
                officeEntity.getState(),
                officeEntity.getCountry(),
                officeEntity.getPostalCode(),
                officeEntity.getTerritory()
        );
    }

    private static OfficeEntity convertOfficeModel2Entity(Office office) {
        return OfficeEntity.builder()
                .officeCode(office.getOfficeCode())
                .addressLine1(office.getAddressLine1())
                .addressLine2(office.getAddressLine2())
                .city(office.getCity())
                .country(office.getCountry())
                .state(office.getState())
                .phone(office.getPhone())
                .postalCode(office.getPostalCode())
                .territory(office.getTerritory())
                .build();
    }

    @Override
    public Office record(Office office) throws OfficeAlreadyExistsException {
        if (officeRepository.findById(office.getOfficeCode()).isPresent()) {
            throw new OfficeAlreadyExistsException();
        }
        OfficeEntity officeEntity = officeRepository.save(
               convertOfficeModel2Entity(office)
        );
        return convertOfficeEntity2Model(officeEntity);
    }

    @Override
    public Office readByOfficeCode(String officeCode) throws OfficeNotFoundException {
        Optional<OfficeEntity> entity = officeRepository.findById(officeCode);
        if (entity.isEmpty()) {
            throw new OfficeNotFoundException(String.format("Cannot find office with office code %s", officeCode));
        }

        return convertOfficeEntity2Model(entity.get());
    }

    @Override
    public Collection<Office> readAll() {
        return officeRepository.findAll().stream().map(OfficeManagerImpl::convertOfficeEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Office modify(Office office) {
        OfficeEntity entity = convertOfficeModel2Entity(office);
        return convertOfficeEntity2Model(officeRepository.save(entity));
    }

    @Override
    public void delete(Office office) {
        officeRepository.delete(convertOfficeModel2Entity(office));
    }
}
