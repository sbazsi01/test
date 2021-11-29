package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Product;
import hu.uni.eku.tzs.service.exceptions.ProductAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ProductNotFoundException;

import java.util.Collection;

public interface ProductManager {
    Product record(Product product) throws ProductAlreadyExistsException;

    Product readByProductCode(String productCode) throws ProductNotFoundException;

    Collection<Product> readAll();

    Collection<Product> readAllByProductLine(String productLine);

    Product modify(Product office);

    void delete(Product office) throws ProductNotFoundException;
}
