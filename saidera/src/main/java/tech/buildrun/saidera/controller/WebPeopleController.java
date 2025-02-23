package tech.buildrun.saidera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.buildrun.saidera.service.BillService;
import tech.buildrun.saidera.service.PeopleService;
import tech.buildrun.saidera.entity.Bill;

@Controller
@RequestMapping("/web/people")
public class WebPeopleController {
    private final PeopleService peopleService;
    private final BillService billService;

    public WebPeopleController(PeopleService peopleService, BillService billService) {
        this.peopleService = peopleService;
        this.billService = billService;
    }

    @PostMapping("/create")
    public String createInitialPerson(@RequestParam String name) {
        CreateBillDto billDto = new CreateBillDto(
                name,
                null,
                null,
                true
        );

        Bill bill = billService.createBill(billDto);
        CreatePeopleDto peopleDto = new CreatePeopleDto(name, bill.getId());
        peopleService.createPeople(peopleDto);

        return "redirect:/web/bills/" + bill.getUniqueId();
    }
}