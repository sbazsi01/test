package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    @Query("SELECT p FROM products p WHERE p.productLine.productLine = ?1")
    Collection<ProductEntity> findAllByProductLine(String productLine);

}