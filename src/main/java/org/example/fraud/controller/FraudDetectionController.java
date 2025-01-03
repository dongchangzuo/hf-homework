package org.example.fraud.controller;

import org.example.fraud.model.Transaction;
import org.example.fraud.service.FraudDetectionService;
import org.example.fraud.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FraudDetectionController {

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionController.class);

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/fraud-detection")
    public boolean detectFraud(Transaction transaction) {
        transactionService.saveTransaction(transaction);
        boolean isFraudulent = fraudDetectionService.detectFraud(transaction);
        if (isFraudulent) {
            log.info("Fraudulent transaction detected: {}", transaction);
        }
        return isFraudulent;
    }
}
