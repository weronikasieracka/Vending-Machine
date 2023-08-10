package cap.s42academy.controller;

import cap.s42academy.model.Transactions;
import cap.s42academy.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/Transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping("/createTransaction")
    public ResponseEntity<String> createTransactionAndCalculateTotalAmount(@RequestBody List<Integer> coinValues) {
        transactionsService.createTransactionAndCalculateTotalAmount(coinValues);
        return ResponseEntity.ok("Transaction created successfully.");
    }

    @PostMapping("/cancelTransaction/{transactionId}")
    public ResponseEntity<String> cancelTransaction(@PathVariable Long transactionId) {
        transactionsService.cancelTransaction(transactionId);
        return ResponseEntity.ok("Transaction canceled successfully.");
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<List<Transactions>> getAllTransactions() {
        List<Transactions> transactionsList = transactionsService.getAllTransactions();
        return ResponseEntity.ok(transactionsList);
    }



}
