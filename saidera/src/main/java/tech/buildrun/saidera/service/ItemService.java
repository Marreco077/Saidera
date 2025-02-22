package tech.buildrun.saidera.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.buildrun.saidera.controller.CreateItemDto;
import tech.buildrun.saidera.entity.Item;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.repository.ItemRepository;
import tech.buildrun.saidera.repository.BillRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final BillRepository billRepository;

    public ItemService(ItemRepository itemRepository, BillRepository billRepository) {
        this.itemRepository = itemRepository;
        this.billRepository = billRepository;
    }

    @Transactional
    public Item createItem(Long billId, CreateItemDto dto) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));

        Item item = new Item(dto.name(), dto.price(), dto.quantity(), bill);
        bill.addItem(item);

        return itemRepository.save(item);
    }

    public List<Item> getItemsByBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        return itemRepository.findByBill(bill);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        Bill bill = item.getBill();
        bill.removeItem(item);
        itemRepository.delete(item);
    }

}