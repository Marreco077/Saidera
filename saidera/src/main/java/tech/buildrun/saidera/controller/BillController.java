package tech.buildrun.saidera.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.entity.People;
import tech.buildrun.saidera.entity.Item;
import tech.buildrun.saidera.service.BillService;
import tech.buildrun.saidera.service.PeopleService;
import tech.buildrun.saidera.service.ItemService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillService billService;
    private final PeopleService peopleService;
    private final ItemService itemService;

    public BillController(BillService billService, PeopleService peopleService, ItemService itemService) {
        this.billService = billService;
        this.peopleService = peopleService;
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody CreateBillDto createBillDto) {
        Bill createdBill = billService.createBill(createBillDto);
        return ResponseEntity.status(201).body(createdBill);
    }

    @GetMapping("/{uniqueId}")
    public ResponseEntity<Bill> getBillByUniqueId(@PathVariable("uniqueId") String uniqueId) {
        var bill = billService.getBillByUniqueId(uniqueId);
        return bill.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Bill>> listBills() {
        var bills = billService.listBills();
        return ResponseEntity.ok(bills);
    }

    @DeleteMapping("/{uniqueId}")
    public ResponseEntity<Void> deleteByUniqueId(@PathVariable("uniqueId") String uniqueId) {
        billService.deleteByUniqueId(uniqueId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{uniqueId}/people")
    public ResponseEntity<PeopleResponseDto> addPersonToBill(
            @PathVariable String uniqueId,
            @RequestBody CreatePeopleDto dto) {
        Bill bill = billService.getBillByUniqueId(uniqueId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        CreatePeopleDto createDto = new CreatePeopleDto(dto.name(), bill.getId());
        People created = peopleService.createPeople(createDto);
        return ResponseEntity.status(201).body(PeopleResponseDto.fromEntity(created));
    }
}