package cap.s42academy.service;
import cap.s42academy.model.Transactions;
import cap.s42academy.model.Coins;
import cap.s42academy.repository.CoinsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CoinsService {
    private final CoinsRepository coinsRepository;

    public void depositCoins(Integer coinValue) {

        Coins coins = coinsRepository.findByCoinValue(coinValue).orElseThrow(() -> new IllegalArgumentException("Invalid coin value."));

        coins.setQuantityAvailable(coins.getQuantityAvailable() + 1);
        coinsRepository.save(coins);

    }

    public void addCoinValue(Integer coinValue) {

            Coins newCoin = new Coins(coinValue, 0);
            coinsRepository.save(newCoin);

    }


}
