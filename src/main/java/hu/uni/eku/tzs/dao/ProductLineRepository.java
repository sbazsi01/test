package hu.uni.eku.tzs.dao;


import hu.uni.eku.tzs.dao.entity.ProductLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineRepository extends JpaRepository<ProductLineEntity, String> {
}
