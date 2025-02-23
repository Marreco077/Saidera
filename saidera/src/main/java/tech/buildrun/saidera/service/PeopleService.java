package tech.buildrun.saidera.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.buildrun.saidera.controller.CreatePeopleDto;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.entity.People;
import tech.buildrun.saidera.repository.BillRepository;
import tech.buildrun.saidera.repository.PeopleRepository;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BillRepository billRepository;

    public PeopleService(PeopleRepository peopleRepository, BillRepository billRepository) {
        this.peopleRepository = peopleRepository;
        this.billRepository = billRepository;
    }

    @Transactional
    public People createPeople(CreatePeopleDto dto) {
        Bill bill = billRepository.findById(dto.billId())
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));

        People people = new People(dto.name(), bill);
        bill.addPerson(people);

        return peopleRepository.save(people);
    }

    public Optional<People> getPeopleById(Long id) {
        return peopleRepository.findById(id);
    }

    public List<People> getPeopleByBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        return peopleRepository.findByBill(bill);
    }

    @Transactional
    public void deletePeople(Long id) {
        People people = peopleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        Bill bill = people.getBill();
        bill.removePerson(people); // Remove da lista do Bill (se houver essa lógica)
        peopleRepository.delete(people);
    }

    @Transactional
    public People togglePaymentStatus(Long id) {
        People people = peopleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        boolean isPaid = !people.isHasPaid();
        people.setHasPaid(isPaid);

        if (isPaid) {
            people.setAmountToPay(BigDecimal.ZERO);
        }

        return peopleRepository.save(people);
    }

    @Transactional
    public People updateAmountToPay(Long peopleId, BigDecimal newAmount) {
        People people = peopleRepository.findById(peopleId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        people.updateAmountToPay(newAmount);
        return peopleRepository.save(people);
    }

    @Transactional
    public People payPerson(Long peopleId) {
        People person = peopleRepository.findById(peopleId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        person.setHasPaid(true);
        person.setAmountToPay(BigDecimal.ZERO);

        return peopleRepository.save(person);
    }


    public BigDecimal getPendingAmount(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        return bill.getTotalPendingAmount();
    }
}
