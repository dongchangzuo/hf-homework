package org.example.fraud.service;

import org.example.fraud.alert.Alert;
import org.example.fraud.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class AlertServiceTest {

    private Alert alert1;
    private Alert alert2;
    private AlertService alertService;

    @BeforeEach
    void setUp() {
        alert1 = Mockito.mock(Alert.class);
        alert2 = Mockito.mock(Alert.class);
        List<Alert> alerts = Arrays.asList(alert1, alert2);
        alertService = new AlertService(alerts);
    }

    @Test
    void testSendAlerts() {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setAccountId("acc123");
        mockTransaction.setAmount(500.0);
        mockTransaction.setTransactionTime(1672956423671L);
        mockTransaction.setTransactionId("tx123");
        alertService.sendAlerts(mockTransaction);
        verify(alert1, times(1)).sendAlert(mockTransaction);
        verify(alert2, times(1)).sendAlert(mockTransaction);
    }
}
