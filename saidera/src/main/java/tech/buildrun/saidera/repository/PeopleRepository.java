package tech.buildrun.saidera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.entity.People;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {
    List<People> findByBill(Bill bill);

    @Query("SELECT p FROM People p WHERE p.bill = :bill AND p.amountToPay > 0")
    List<People> findPeopleWithDebt(@Param("bill") Bill bill);
}
