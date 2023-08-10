package cap.s42academy.service;

import cap.s42academy.model.Coins;
import cap.s42academy.model.Products;
import cap.s42academy.model.Transactions;
import cap.s42academy.repository.CoinsRepository;
import cap.s42academy.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private CoinsRepository coinsRepository;
    @Autowired
    private  ProductService productsService;
    @Autowired
    public TransactionsService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Transactional
    public void createTransactionAndCalculateTotalAmount(List<Integer> coinValues) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        Coins coin = null;
        for (Integer coinValue : coinValues) {
            coin = coinsRepository.findByCoinValue(coinValue)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid coin value."));

            totalAmount = totalAmount.add(BigDecimal.valueOf(coinValue));
        }

        Transactions transaction = new Transactions();
        transaction.setCoin(coin);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setAmountPaid(totalAmount);
        transaction.setChangeReturned(BigDecimal.ZERO);
        transaction.setCanceled(false);

        transactionsRepository.save(transaction);
    }
    public void cancelTransaction(Long transactionId) {
        Transactions transaction = transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found."));

        if (!transaction.isCanceled()) {
            transaction.setCanceled(true);

            Coins coin = transaction.getCoin();
            coin.setQuantityAvailable(coin.getQuantityAvailable() + 1);

            coinsRepository.save(coin);
            transactionsRepository.save(transaction);
        } else {
            throw new IllegalStateException("Transaction is already canceled.");
        }
    }


    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    public Transactions getTransactionById(Long transactionId) {
        return transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }
}
