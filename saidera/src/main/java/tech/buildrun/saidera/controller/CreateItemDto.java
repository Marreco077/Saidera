package tech.buildrun.saidera.controller;

import java.math.BigDecimal;
import java.util.List;

public record CreateItemDto(
        String name,
        BigDecimal price,
        Integer quantity,
        List<Long> peopleIds
) {}
