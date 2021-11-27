package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
}
