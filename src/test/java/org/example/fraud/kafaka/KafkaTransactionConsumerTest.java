package org.example.fraud.kafaka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.fraud.model.Transaction;
import org.example.fraud.service.AlertService;
import org.example.fraud.service.FraudDetectionService;
import org.example.fraud.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class KafkaTransactionConsumerTest {

    @Mock
    private FraudDetectionService fraudDetectionService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AlertService alertService;

    @InjectMocks
    private KafkaTransactionConsumer kafkaTransactionConsumer;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConsumeTransaction_FraudulentTransaction() throws Exception {
        Transaction transaction = new Transaction("123", "account1", 1000.0, System.currentTimeMillis());
        String transactionJson = objectMapper.writeValueAsString(transaction);
        ConsumerRecord<String, String> record = new ConsumerRecord<>("transactions", 0, 0, "key", transactionJson);
        when(fraudDetectionService.detectFraud(transaction)).thenReturn(true);
        kafkaTransactionConsumer.consumeTransaction(record);
        verify(transactionService).saveTransaction(transaction);
        verify(fraudDetectionService).detectFraud(transaction);
        verify(alertService).sendAlerts(transaction);
    }

    @Test
    void testConsumeTransaction_NonFraudulentTransaction() throws Exception {
        Transaction transaction = new Transaction("123", "account1", 500.0, System.currentTimeMillis());
        String transactionJson = objectMapper.writeValueAsString(transaction);
        ConsumerRecord<String, String> record = new ConsumerRecord<>("transactions", 0, 0, "key", transactionJson);
        when(fraudDetectionService.detectFraud(transaction)).thenReturn(false);
        kafkaTransactionConsumer.consumeTransaction(record);
        verify(transactionService).saveTransaction(transaction);
        verify(fraudDetectionService).detectFraud(transaction);
        verify(alertService, never()).sendAlerts(transaction);
    }

    @Test
    void testConsumeTransaction_InvalidTransaction() {
        String invalidTransactionJson = "{ invalid json }";
        ConsumerRecord<String, String> record = new ConsumerRecord<>("transactions", 0, 0, "key", invalidTransactionJson);
        kafkaTransactionConsumer.consumeTransaction(record);
        verify(transactionService, never()).saveTransaction(any());
        verify(fraudDetectionService, never()).detectFraud(any());
        verify(alertService, never()).sendAlerts(any());
    }
}
