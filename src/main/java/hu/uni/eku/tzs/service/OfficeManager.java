package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.exceptions.OfficeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OfficeNotFoundException;

import java.util.Collection;

public interface OfficeManager {
    Office record(Office office) throws OfficeAlreadyExistsException;

    Office readByOfficeCode(int officeCode) throws OfficeNotFoundException;

    Collection<Office> readAll();

    Office modify(Office office);

    void delete(Office office) throws OfficeNotFoundException;
}
