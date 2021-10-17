package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.PaymentEntity;
import hu.uni.eku.tzs.dao.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, PaymentId> {
}
