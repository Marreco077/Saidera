package tech.buildrun.saidera.controller;

import java.math.BigDecimal;

public record CreateItemDto (
    String name,
    int quantity,
    BigDecimal price
) {}