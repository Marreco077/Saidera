package tech.buildrun.saidera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.saidera.entity.Bill;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByUniqueId(String uniqueId);
}