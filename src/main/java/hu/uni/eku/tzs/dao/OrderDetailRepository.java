package hu.uni.eku.tzs.dao;



import hu.uni.eku.tzs.dao.entity.OrderDetailEntity;
import hu.uni.eku.tzs.dao.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, OrderDetailId> {
}
