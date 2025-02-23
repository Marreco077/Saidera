package tech.buildrun.saidera.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.saidera.entity.Item;
import tech.buildrun.saidera.service.ItemService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bills/{billUniqueId}/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(
            @PathVariable String billUniqueId,
            @RequestBody CreateItemDto createItemDto) {

        Item createdItem = itemService.addItemToBill(
                billUniqueId,
                createItemDto.name(),
                createItemDto.price(),
                createItemDto.quantity(),
                createItemDto.peopleIds()
        );

        return ResponseEntity.status(201).body(ItemResponseDto.fromEntity(createdItem));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItemsByBill(@PathVariable String billUniqueId) {
        List<Item> items = itemService.getItemsByBill(billUniqueId);
        List<ItemResponseDto> response = items.stream()
                .map(ItemResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable String billUniqueId,
            @PathVariable Long itemId) {
        itemService.deleteItem(billUniqueId, itemId);
        return ResponseEntity.noContent().build();
    }
}
