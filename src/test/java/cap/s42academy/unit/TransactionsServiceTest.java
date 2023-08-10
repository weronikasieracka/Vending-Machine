package cap.s42academy.unit;

import cap.s42academy.model.Coins;
import cap.s42academy.model.Products;
import cap.s42academy.model.Transactions;
import cap.s42academy.repository.CoinsRepository;
import cap.s42academy.repository.TransactionsRepository;
import cap.s42academy.service.TransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionsServiceTest {

    @Mock
    private CoinsRepository coinsRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private TransactionsService transactionsService;
    @Captor
    private ArgumentCaptor<Transactions> transactionsCaptor;


    @Test
    public void testCancelTransaction_AlreadyCanceled() {
        Long transactionId = 1L;
        Transactions transaction = new Transactions();
        transaction.setTransactionId(transactionId);
        transaction.setCanceled(true);

        when(transactionsRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        assertThrows(IllegalStateException.class, () -> {
            transactionsService.cancelTransaction(transactionId);
        });
        verify(transactionsRepository, never()).save(any(Transactions.class));
        verify(coinsRepository, never()).save(any(Coins.class));
    }
    @Test
    public void testCancelTransaction_TransactionNotFound() {
        Long transactionId = 1L;

        when(transactionsRepository.findById(transactionId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            transactionsService.cancelTransaction(transactionId);
        });
        verify(transactionsRepository, never()).save(any(Transactions.class));
        verify(coinsRepository, never()).save(any(Coins.class));
    }

}
