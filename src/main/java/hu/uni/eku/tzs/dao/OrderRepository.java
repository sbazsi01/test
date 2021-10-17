package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
