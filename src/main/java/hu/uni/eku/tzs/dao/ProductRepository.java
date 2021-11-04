package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    @Query(value = "SELECT * FROM PRODUCTS WHERE productLine = ?1", nativeQuery = true)
    Collection<ProductEntity> findAllByProductLine(String productLine);

}