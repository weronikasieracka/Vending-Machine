package cap.s42academy.service;

import cap.s42academy.model.Income;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cap.s42academy.repository.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
    public void depositIncome(Integer incomeAmount) {
        Income income = new Income();
        income.setTransactionTime(LocalDateTime.now());
        income.setIncomeAmount(incomeAmount);

        incomeRepository.save(income);
    }

    public List<Income> getAllIncome() {
        return incomeRepository.findAll();
    }
}
