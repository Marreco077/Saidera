package tech.buildrun.saidera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.entity.People;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {
    List<People> findByBill(Bill bill);
}
