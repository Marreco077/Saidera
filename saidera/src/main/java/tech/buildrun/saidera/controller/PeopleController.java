package tech.buildrun.saidera.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.saidera.entity.People;
import tech.buildrun.saidera.service.PeopleService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PostMapping("/bills/{billId}")
    public ResponseEntity<PeopleResponseDto> createPeople(
            @PathVariable Long billId,
            @RequestBody CreatePeopleDto dto) {
        var createDto = new CreatePeopleDto(dto.name(), billId);
        People created = peopleService.createPeople(createDto);
        return ResponseEntity.status(201).body(PeopleResponseDto.fromEntity(created));
    }

    @GetMapping("/bills/{billId}")
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


    @PatchMapping("/{peopleId}/pay")
    public ResponseEntity<People> payPerson(@PathVariable Long peopleId) {
        People updated = peopleService.payPerson(peopleId);
        return ResponseEntity.ok(updated);
    }


    @PatchMapping("/{peopleId}/amount")
    public ResponseEntity<People> updateAmountToPay(
            @PathVariable Long peopleId,
            @RequestParam BigDecimal newAmount) {
        People updated = peopleService.updateAmountToPay(peopleId, newAmount);
        return ResponseEntity.ok(updated);
    }
}