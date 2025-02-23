package tech.buildrun.saidera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.buildrun.saidera.entity.Bill;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByUniqueId(String uniqueId);
    void deleteByUniqueId(String uniqueId);

    List<Bill> findByIsActiveTrue();
    List<Bill> findByExpirationTimeBefore(LocalDateTime dateTime);
    boolean existsByUniqueId(String uniqueId);
}