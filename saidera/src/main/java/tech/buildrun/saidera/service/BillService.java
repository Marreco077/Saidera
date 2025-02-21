package tech.buildrun.saidera.service;

import org.springframework.stereotype.Service;
import tech.buildrun.saidera.controller.CreateBillDto;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.repository.BillRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill createBill(CreateBillDto dto) {
        Bill bill = new Bill(dto.creatorName());

        if (dto.creationTime() != null) {
            bill.setCreationTime(dto.creationTime());
        }

        if (dto.expirationTime() != null) {
            bill.setExpirationTime(dto.expirationTime());
        }

        if (dto.isActive() != null) {
            bill.setIsActive(dto.isActive());
        }

        return billRepository.save(bill);
    }

    public Optional<Bill> getBillById(String billId) {

        return billRepository.findById(Long.parseLong(billId));
    }

    public List<Bill> listBills() {
        return billRepository.findAll();
    }

    public void deleteById(String billId) {
        var billExists = billRepository.existsById(Long.parseLong(billId));
        if (billExists) {
            billRepository.deleteById(Long.parseLong(billId));
        }
    }
}