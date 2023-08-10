package cap.s42academy.unit;

import cap.s42academy.model.Coins;
import cap.s42academy.repository.CoinsRepository;
import cap.s42academy.service.CoinsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoinsServiceTest {
    @Mock
    private CoinsRepository coinsRepository;

    @InjectMocks
    private CoinsService coinsService;

    @Test
    public void testDepositCoins_Success() {
        Integer coinValue = 10;
        Coins coins = new Coins();
        coins.setQuantityAvailable(5);

        when(coinsRepository.findByCoinValue(coinValue)).thenReturn(Optional.of(coins));
        when(coinsRepository.save(coins)).thenReturn(coins);

        coinsService.depositCoins(coinValue);

        assertEquals(6, coins.getQuantityAvailable());
        verify(coinsRepository, times(1)).save(coins);
    }
    @Test
    public void testDepositCoins_InvalidCoinValue() {
        Integer coinValue = 10;

        when(coinsRepository.findByCoinValue(coinValue)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            coinsService.depositCoins(coinValue);
        });

        verify(coinsRepository, never()).save(any(Coins.class));
    }
    @Test
    public void testAddCoinValue_Success() {
        Integer coinValue = 10;
        Coins newCoin = new Coins(coinValue, 0);

        when(coinsRepository.save(newCoin)).thenReturn(newCoin);

        coinsService.addCoinValue(coinValue);

        verify(coinsRepository, times(1)).save(newCoin);
    }

}
