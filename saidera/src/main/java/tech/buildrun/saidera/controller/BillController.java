package tech.buildrun.saidera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.service.BillService;

import java.util.List;


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
        var user =billService.getBillById(billId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Bill>> listBills() {
        var bills = billService.listBills();

        return ResponseEntity.ok(bills);
    }


    @DeleteMapping("/{billId}")
    public ResponseEntity<Void> deleteById(@PathVariable("billId") String billId) {
        billService.deleteById(billId);
        return ResponseEntity.noContent().build();
    }
}