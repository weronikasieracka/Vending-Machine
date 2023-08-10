package cap.s42academy.controller;

import cap.s42academy.model.Income;
import cap.s42academy.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/income")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    @GetMapping("/getIncome")
    public ResponseEntity<List<Income>> getAllIncome() {
        List<Income> incomeList = incomeService.getAllIncome();
        return ResponseEntity.ok(incomeList);
    }
    @PostMapping("/deposit")
    public ResponseEntity<String> depositIncome(@RequestParam BigDecimal incomeAmount) {
        incomeService.depositIncome(incomeAmount);
        return ResponseEntity.ok("Income deposited successfully.");
    }
}
