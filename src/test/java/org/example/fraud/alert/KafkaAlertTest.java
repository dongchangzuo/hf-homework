package org.example.fraud.alert;

import org.example.fraud.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class KafkaAlertTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private KafkaAlert kafkaAlert;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kafkaAlert = new KafkaAlert(kafkaTemplate);
    }

    @Test
    void testSendAlert() {
        Transaction mockTransaction = mock(Transaction.class);
        String transactionJson = "{\"accountId\":\"acc123\",\"amount\":1000.0,\"transactionId\":\"tx123\",\"transactionTime\":1672956423671}";
        when(mockTransaction.toJson()).thenReturn(transactionJson);
        kafkaAlert.sendAlert(mockTransaction);
        verify(kafkaTemplate, times(1)).send("fraud-alerts", transactionJson);
        verify(kafkaTemplate, times(1)).flush();
    }
}
