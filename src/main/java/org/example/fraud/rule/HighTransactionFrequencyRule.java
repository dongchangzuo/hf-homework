package org.example.fraud.rule;

import org.example.fraud.model.Transaction;
import org.example.fraud.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class HighTransactionFrequencyRule implements Rule {

    private static final Logger log = LoggerFactory.getLogger(HighTransactionFrequencyRule.class);

    private final TransactionService transactionService;


    @Value("${fraud.rules.highTransactionFrequency.timeWindowMinutes:5}")
    private int timeWindowMinutes;

    @Value("${fraud.rules.highTransactionFrequency.maxTransactions:10}")
    private int maxTransactions;

    @Autowired
    public HighTransactionFrequencyRule(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public boolean apply(Transaction transaction) {
        long end = transaction.getTransactionTime();
        long start = end - (long) timeWindowMinutes * 60 * 1000;
        long transactionCount = transactionService.countTransactionsInTimeRange(transaction.getAccountId(), start, end);
        log.info("Transaction count in last {} minutes: {}", timeWindowMinutes, transactionCount);
        return transactionCount > maxTransactions;
    }

    @Override
    public String getRuleName() {
        return "HighTransactionFrequencyRule";
    }
}
