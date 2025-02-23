package tech.buildrun.saidera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.buildrun.saidera.service.BillService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/web")
public class PageController {
    private final BillService billService;

    public PageController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/bills/{uniqueId}")
    public String billDetails(@PathVariable String uniqueId, Model model) {
        var billOptional = billService.getBillByUniqueId(uniqueId);

        if (billOptional.isPresent()) {
            var bill = billOptional.get();

            // Soma de todos os itens (price * quantity)
            var total = bill.getItems().stream()
                    .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            model.addAttribute("bill", bill);
            model.addAttribute("total", total);
            return "bill";
        }

        return "redirect:/web/";
    }


}