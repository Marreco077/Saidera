package tech.buildrun.saidera.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.entity.Item;
import tech.buildrun.saidera.entity.People;
import tech.buildrun.saidera.repository.BillRepository;
import tech.buildrun.saidera.repository.ItemRepository;
import tech.buildrun.saidera.repository.PeopleRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final BillRepository billRepository;
    private final PeopleRepository peopleRepository;

    public ItemService(ItemRepository itemRepository,
                       BillRepository billRepository,
                       PeopleRepository peopleRepository) {
        this.itemRepository = itemRepository;
        this.billRepository = billRepository;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public Item addItemToBill(String billUniqueId,
                              String itemName,
                              BigDecimal price,
                              int quantity,
                              List<Long> peopleIds) {

        // 1) Verifica se existe a Bill
        Optional<Bill> billOptional = billRepository.findByUniqueId(billUniqueId);
        if (billOptional.isEmpty()) {
            throw new RuntimeException("Bill not found");
        }
        Bill bill = billOptional.get();

        List<People> consumers;
        if (peopleIds == null || peopleIds.isEmpty()) {
            // Se não veio nenhuma lista de IDs, divide para todos da Bill
            consumers = peopleRepository.findByBill(bill);
            if (consumers.isEmpty()) {
                throw new RuntimeException("No people found in this bill");
            }
        } else {
            consumers = peopleRepository.findAllById(peopleIds);
            if (consumers.isEmpty()) {
                throw new RuntimeException("No valid people found for this item");
            }
        }

        Item item = new Item();
        item.setName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setBill(bill);
        item.setConsumers(consumers);
        itemRepository.save(item);

        BigDecimal totalCost = price.multiply(BigDecimal.valueOf(quantity));
        BigDecimal sharePerPerson = totalCost.divide(
                BigDecimal.valueOf(consumers.size()),
                2, // Duas casas decimais
                RoundingMode.HALF_UP
        );

        for (People person : consumers) {
            BigDecimal newAmount = person.getAmountToPay().add(sharePerPerson);
            person.setAmountToPay(newAmount);

            person.addConsumedItem(item);

            peopleRepository.save(person);
        }

        return item;
    }

    public List<Item> getItemsByBill(String billUniqueId) {
        Optional<Bill> billOptional = billRepository.findByUniqueId(billUniqueId);
        if (billOptional.isEmpty()) {
            throw new RuntimeException("Bill not found");
        }
        return itemRepository.findByBill(billOptional.get());
    }

    @Transactional
    public void deleteItem(String billUniqueId, Long itemId) {
        Optional<Bill> billOptional = billRepository.findByUniqueId(billUniqueId);
        if (billOptional.isEmpty()) {
            throw new RuntimeException("Bill not found");
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getBill().getUniqueId().equals(billUniqueId)) {
            throw new RuntimeException("Item does not belong to this bill");
        }

        itemRepository.delete(item);
    }
}
