package cap.s42academy.controller;

import cap.s42academy.service.CoinsService;
import cap.s42academy.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/coins")
@RequiredArgsConstructor
public class CoinsController {
    private final CoinsService coinsService;
    private final IncomeService incomeService;

    private static final List<BigDecimal> VALID_COIN_VALUES = Arrays.asList(
            new BigDecimal("1"), new BigDecimal("5"),
            new BigDecimal("10"), new BigDecimal("20"),
            new BigDecimal("50"), new BigDecimal("100")
    );
    @PostMapping("/deposit")
    public ResponseEntity<String> depositCoin(@RequestParam Integer coinValue) {
        try {
            coinsService.depositCoins(coinValue);
            incomeService.depositIncome(coinValue);
            return ResponseEntity.ok("Coin deposited successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid coin value.");
        }
    }
    @PostMapping("/addValue")
    public ResponseEntity<String> addCoinValue(@RequestParam Integer coinValue) {
        coinsService.addCoinValue(coinValue);
        return ResponseEntity.ok("Coin value added successfully.");
    }

}
