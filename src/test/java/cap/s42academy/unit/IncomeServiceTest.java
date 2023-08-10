package cap.s42academy.unit;
import cap.s42academy.model.Income;
import cap.s42academy.repository.IncomeRepository;
import cap.s42academy.service.IncomeService;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {
    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeService incomeService;

    @Test
    public void testDepositIncome() {
        // Arrange
        Integer incomeAmount = 100;
        LocalDateTime currentDateTime = LocalDateTime.now();

        Income savedIncome = new Income();
        savedIncome.setTransactionTime(currentDateTime);
        savedIncome.setIncomeAmount(incomeAmount);
        when(incomeRepository.save(any(Income.class))).thenReturn(savedIncome);

        // Act
        incomeService.depositIncome(incomeAmount);

        // Assert
        verify(incomeRepository, times(1)).save(any(Income.class));
    }
}
