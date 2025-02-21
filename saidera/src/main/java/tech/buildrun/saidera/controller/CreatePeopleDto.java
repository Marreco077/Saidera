// CreatePeopleDto.java
package tech.buildrun.saidera.controller;

public record CreatePeopleDto(
        String name,
        Long billId
) {}