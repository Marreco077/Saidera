package tech.buildrun.saidera.controller;

import tech.buildrun.saidera.entity.Item;

public record ItemResponseDto(
        Long id,
        String name,
        java.math.BigDecimal price,
        Integer quantity
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