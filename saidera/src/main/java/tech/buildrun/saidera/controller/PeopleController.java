package tech.buildrun.saidera.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.saidera.entity.People;
import tech.buildrun.saidera.service.PeopleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bills/{billId}/people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PostMapping
    public ResponseEntity<PeopleResponseDto> createPeople(
            @PathVariable Long billId,
            @RequestBody CreatePeopleDto dto) {
        var createDto = new CreatePeopleDto(dto.name(), billId);
        People created = peopleService.createPeople(createDto);
        return ResponseEntity.status(201).body(PeopleResponseDto.fromEntity(created));
    }

    @GetMapping
    public ResponseEntity<List<PeopleResponseDto>> getPeopleByBill(@PathVariable Long billId) {
        List<People> people = peopleService.getPeopleByBill(billId);
        List<PeopleResponseDto> response = people.stream()
                .map(PeopleResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{peopleId}")
    public ResponseEntity<Void> deletePeople(@PathVariable Long peopleId) {
        peopleService.deletePeople(peopleId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{peopleId}/payment")
    public ResponseEntity<People> togglePaymentStatus(@PathVariable Long peopleId) {
        People updated = peopleService.togglePaymentStatus(peopleId);
        return ResponseEntity.ok(updated);
    }
}