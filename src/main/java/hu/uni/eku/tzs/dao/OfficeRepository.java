package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<OfficeEntity, String> {
}
