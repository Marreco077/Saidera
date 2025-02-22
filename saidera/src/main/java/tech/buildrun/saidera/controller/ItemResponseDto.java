package tech.buildrun.saidera.controller;

import tech.buildrun.saidera.entity.Item;

import java.math.BigDecimal;

public record ItemResponseDto (
    Long id,
    String name,
    BigDecimal price,
    int quantity
) {
    public static ItemResponseDto fromEntity(Item item) {
        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getQuantity()
        );
    }
}
