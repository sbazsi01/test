package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.ProductLines;
import hu.uni.eku.tzs.service.exceptions.ProductLinesAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ProductLinesNotFoundException;

import java.util.Collection;

public interface ProductLinesManager {
    ProductLines record(ProductLines productLines) throws ProductLinesAlreadyExistsException;

    ProductLines readByProductLine(String productLine) throws ProductLinesNotFoundException;

    Collection<ProductLines> readAll();

    ProductLines modify(ProductLines productLines);

    void delete(ProductLines productLines) throws ProductLinesNotFoundException;
}
