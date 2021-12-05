package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ProductRepository;
import hu.uni.eku.tzs.dao.entity.ProductEntity;
import hu.uni.eku.tzs.dao.entity.ProductLinesEntity;
import hu.uni.eku.tzs.model.Product;
import hu.uni.eku.tzs.model.ProductLines;
import hu.uni.eku.tzs.service.exceptions.ProductAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductManagerImpl implements ProductManager {

    private final ProductRepository productRepository;

    public static ProductLines convertProductLinesEntity2Model(ProductLinesEntity productLinesEntity) {
        return new ProductLines(
                productLinesEntity.getProductLine(),
                productLinesEntity.getTextDescription(),
                productLinesEntity.getHtmlDescription(),
                productLinesEntity.getImage()
        );
    }

    public static ProductLinesEntity convertProductLinesModel2Entity(ProductLines productLines) {
        return ProductLinesEntity.builder()
                .productLine(productLines.getProductLine())
                .textDescription(productLines.getTextDescription())
                .htmlDescription(productLines.getHtmlDescription())
                .image(productLines.getImage())
                .build();
    }

    public static Product convertProductEntity2Model(ProductEntity productEntity) {
        return new Product(
                productEntity.getProductCode(),
                productEntity.getProductName(),
                convertProductLinesEntity2Model(productEntity.getProductLine()),
                productEntity.getProductScale(),
                productEntity.getProductVendor(),
                productEntity.getProductDescription(),
                productEntity.getQuantityInStock(),
                productEntity.getBuyPrice(),
                productEntity.getMsrp()
        );
    }

    public static ProductEntity convertProductModel2Entity(Product product) {
        return ProductEntity.builder()
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productLine(convertProductLinesModel2Entity(product.getProductLine()))
                .productScale(product.getProductScale())
                .productVendor(product.getProductVendor())
                .productDescription(product.getProductDescription())
                .quantityInStock(product.getQuantityInStock())
                .buyPrice(product.getBuyPrice())
                .msrp(product.getMsrp())
                .build();
    }

    @Override
    public Product record(Product product) throws ProductAlreadyExistsException {
        if (productRepository.findById(product.getProductCode()).isPresent()) {
            throw new ProductAlreadyExistsException();
        }
        ProductEntity officeEntity = productRepository.save(
               convertProductModel2Entity(product)
        );
        return convertProductEntity2Model(officeEntity);
    }

    @Override
    public Product readByProductCode(String productCode) throws ProductNotFoundException {
        Optional<ProductEntity> entity = productRepository.findById(productCode);
        if (entity.isEmpty()) {
            throw new ProductNotFoundException(String.format("Cannot find product with product code %s", productCode));
        }

        return convertProductEntity2Model(entity.get());
    }

    @Override
    public Collection<Product> readAll() {
        return productRepository.findAll().stream().map(ProductManagerImpl::convertProductEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Product> readAllByProductLine(String productLine) {
        return productRepository.findAllByProductLine(productLine).stream()
                .map(ProductManagerImpl::convertProductEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Product modify(Product product) {
        ProductEntity entity = convertProductModel2Entity(product);
        return convertProductEntity2Model(productRepository.save(entity));
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(convertProductModel2Entity(product));
    }
}
