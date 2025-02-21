package tech.buildrun.saidera.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.buildrun.saidera.controller.CreatePeopleDto;
import tech.buildrun.saidera.entity.People;
import tech.buildrun.saidera.entity.Bill;
import tech.buildrun.saidera.repository.PeopleRepository;
import tech.buildrun.saidera.repository.BillRepository;

import jakarta.persistence.EntityNotFoundException;
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
        bill.removePerson(people);
        peopleRepository.delete(people);
    }

    @Transactional
    public People togglePaymentStatus(Long id) {
        People people = peopleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        people.setHasPaid(!people.isHasPaid());
        return peopleRepository.save(people);
    }
}