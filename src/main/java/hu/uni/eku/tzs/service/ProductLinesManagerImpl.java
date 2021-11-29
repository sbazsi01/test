package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ProductLinesRepository;
import hu.uni.eku.tzs.dao.entity.ProductLinesEntity;
import hu.uni.eku.tzs.model.ProductLines;
import hu.uni.eku.tzs.service.exceptions.ProductLinesAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ProductLinesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductLinesManagerImpl implements ProductLinesManager {

    private final ProductLinesRepository productLinesRepository;

    private static ProductLines convertProductLinesEntity2Model(ProductLinesEntity productLinesEntity) {
        return new ProductLines(
                productLinesEntity.getProductLine(),
                productLinesEntity.getTextDescription(),
                productLinesEntity.getHtmlDescription(),
                productLinesEntity.getImage()
        );
    }

    private static ProductLinesEntity convertProductLinesModel2Entity(ProductLines productLines) {
        return ProductLinesEntity.builder()
                .productLine(productLines.getProductLine())
                .textDescription(productLines.getTextDescription())
                .htmlDescription(productLines.getHtmlDescription())
                .image(productLines.getImage())
                .build();
    }

    @Override
    public ProductLines record(ProductLines productLines) throws ProductLinesAlreadyExistsException {
        if (productLinesRepository.findById(productLines.getProductLine()).isPresent()) {
            throw new ProductLinesAlreadyExistsException();
        }
        ProductLinesEntity productLinesEntity = productLinesRepository.save(
                convertProductLinesModel2Entity(productLines)
        );
        return convertProductLinesEntity2Model(productLinesEntity);
    }

    @Override
    public ProductLines readByProductLine(String productLine) throws ProductLinesNotFoundException {
        Optional<ProductLinesEntity> entity = productLinesRepository.findById(productLine);
        if (entity.isEmpty()) {
            throw new ProductLinesNotFoundException(String.format("Cannot find product lines with ...", productLine));
        }

        return convertProductLinesEntity2Model(entity.get());
    }

    @Override
    public Collection<ProductLines> readAll() {
        return productLinesRepository.findAll().stream().map(ProductLinesManagerImpl::convertProductLinesEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public ProductLines modify(ProductLines productLines) {
        ProductLinesEntity entity = convertProductLinesModel2Entity(productLines);
        return convertProductLinesEntity2Model(productLinesRepository.save(entity));
    }

    @Override
    public void delete(ProductLines productLines) throws ProductLinesNotFoundException {
        productLinesRepository.delete(convertProductLinesModel2Entity(productLines));
    }
}
