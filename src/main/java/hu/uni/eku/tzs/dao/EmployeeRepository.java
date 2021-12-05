package hu.uni.eku.tzs.dao;

import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    @Query(
        value = "SELECT e FROM employees e WHERE e.office.officeCode = ?1")
    Collection<EmployeeEntity> findAllByOffice(String officeCode);
}