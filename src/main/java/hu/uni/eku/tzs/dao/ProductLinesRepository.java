package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.ProductLinesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLinesRepository extends JpaRepository<ProductLinesEntity, String> {
}
