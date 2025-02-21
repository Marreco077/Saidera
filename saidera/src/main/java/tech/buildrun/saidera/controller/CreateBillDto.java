package tech.buildrun.saidera.controller;

import java.time.LocalDateTime;

public record CreateBillDto(
        String creatorName,
        LocalDateTime creationTime,
        LocalDateTime expirationTime,
        Boolean isActive
) {}