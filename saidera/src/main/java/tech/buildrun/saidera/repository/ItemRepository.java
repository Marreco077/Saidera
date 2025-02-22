package tech.buildrun.saidera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBill(Bill bill);
}
