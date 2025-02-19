package tech.buildrun.saidera.service;

import org.springframework.stereotype.Service;
import tech.buildrun.saidera.controller.CreateBillDto;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.repository.BillRepository;

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

    public Bill getBillById(String billId) {
        return billRepository.findByUniqueId(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + billId));
    }
}