package org.example.fraud.kafaka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.fraud.model.Transaction;
import org.example.fraud.service.AlertService;
import org.example.fraud.service.FraudDetectionService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.fraud.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KafkaTransactionConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaTransactionConsumer.class);

    private final FraudDetectionService fraudDetectionService;

    private final TransactionService transactionService;

    private final AlertService alertServices;

    @Autowired
    public KafkaTransactionConsumer(FraudDetectionService fraudDetectionService, TransactionService transactionService, AlertService alertServices) {
        this.fraudDetectionService = fraudDetectionService;
        this.transactionService = transactionService;
        this.alertServices = alertServices;
    }

    @KafkaListener(topics = "transactions", groupId = "fraud-detection-group")
    public void consumeTransaction(ConsumerRecord<String, String> record) {
        String message = record.value();
        try {
            Transaction transaction = parseTransaction(message);
            transactionService.saveTransaction(transaction);
            boolean isFraudulent = fraudDetectionService.detectFraud(transaction);
            if (isFraudulent) {
                log.info("Fraudulent transaction detected: {}", transaction);
                sendFraudAlerts(transaction);
            }
        } catch (Exception e) {
            log.error("Error processing transaction: {}", e.getMessage());
        }
    }

    private void sendFraudAlerts(Transaction transaction) {
        alertServices.sendAlerts(transaction);
    }

    private Transaction parseTransaction(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(message, Transaction.class);
        } catch (Exception e) {
            log.error("Failed to parse transaction: {}", message);
            throw new RuntimeException("Failed to parse transaction", e);
        }
    }
}
