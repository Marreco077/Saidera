package tech.buildrun.saidera.service;

import org.springframework.stereotype.Service;
import tech.buildrun.saidera.controller.CreateBillDto;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.repository.BillRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository billRepository;

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

        Bill savedBill = billRepository.save(bill);

        System.out.println("Bill criada com ID: " + savedBill.getId());

        return savedBill;
    }

    public Optional<Bill> getBillByUniqueId(String uniqueId) {
        return billRepository.findByUniqueId(uniqueId);
    }

    protected Optional<Bill> getBillByInternalId(Long id) {
        return billRepository.findById(id);
    }

    public List<Bill> listBills() {
        return billRepository.findAll();
    }

    public void deleteByUniqueId(String uniqueId) {
        billRepository.findByUniqueId(uniqueId)
                .ifPresent(billRepository::delete);
    }

    public boolean isBillExpired(String uniqueId) {
        return getBillByUniqueId(uniqueId)
                .map(bill -> bill.getExpirationTime().isBefore(LocalDateTime.now()))
                .orElse(true); // Se não encontrar, considera expirada
    }
}