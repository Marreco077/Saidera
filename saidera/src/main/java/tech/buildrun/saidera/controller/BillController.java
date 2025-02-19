package tech.buildrun.saidera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.service.BillService;

@RestController
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody CreateBillDto createBillDto) {
        Bill createdBill = billService.createBill(createBillDto);
        return ResponseEntity.status(201).body(createdBill);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<Bill> getBillById(@PathVariable("billId") String billId) {
        Bill bill = billService.getBillById(billId);
        return ResponseEntity.ok(bill);
    }
}