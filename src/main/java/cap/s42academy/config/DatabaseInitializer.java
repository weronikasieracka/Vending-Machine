package cap.s42academy.config;

import cap.s42academy.model.Coins;
import cap.s42academy.repository.CoinsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final CoinsRepository coinRepository;

    public DatabaseInitializer(CoinsRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Override
    public void run(String... args) {
        List<Integer> coinValues = Arrays.asList(1, 5, 10, 20, 50, 100);

        coinValues.forEach(value -> {
            Coins coin = new Coins(value, 0);
            coinRepository.save(coin);
        });
    }
}

