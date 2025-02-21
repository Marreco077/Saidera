package tech.buildrun.saidera.controller;

import tech.buildrun.saidera.entity.People;

public record PeopleResponseDto(
        Long id,
        String name,
        boolean hasPaid
) {
    public static PeopleResponseDto fromEntity(People people) {
        return new PeopleResponseDto(
                people.getId(),
                people.getName(),
                people.isHasPaid()
        );
    }
}