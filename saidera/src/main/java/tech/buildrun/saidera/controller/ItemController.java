package tech.buildrun.saidera.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.saidera.entity.Item;
import tech.buildrun.saidera.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bills/{billId}/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(
            @PathVariable Long billId,
            @RequestBody CreateItemDto createItemDto) {
        Item createdItem = itemService.createItem(billId, createItemDto);
        return ResponseEntity.status(201).body(ItemResponseDto.fromEntity(createdItem));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItemsByBill(@PathVariable Long billId) {
        List<Item> items = itemService.getItemsByBill(billId);
        List<ItemResponseDto> response = items.stream()
                .map(ItemResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}