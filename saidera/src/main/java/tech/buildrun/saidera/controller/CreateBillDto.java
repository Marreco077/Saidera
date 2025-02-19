package tech.buildrun.saidera.controller;

import java.time.LocalDateTime;

public record CreateBillDto(
        @NotBlank(message = "O nome do criador é obrigatório")
        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
        String creatorName
) {}