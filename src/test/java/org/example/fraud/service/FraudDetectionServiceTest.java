package org.example.fraud.service;

import org.example.fraud.model.Transaction;
import org.example.fraud.rule.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class FraudDetectionServiceTest {

    private Rule rule1;
    private Rule rule2;
    private FraudDetectionService fraudDetectionService;

    @BeforeEach
    void setUp() {
        rule1 = Mockito.mock(Rule.class);
        rule2 = Mockito.mock(Rule.class);
        List<Rule> fraudRules = Arrays.asList(rule1, rule2);
        fraudDetectionService = new FraudDetectionService(fraudRules);
    }

    @Test
    void testDetectFraud_FraudulentTransaction() {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setAccountId("acc123");
        mockTransaction.setAmount(1000.0);
        mockTransaction.setTransactionId("tx123");
        mockTransaction.setTransactionTime(1672956423671L);
        when(rule1.apply(mockTransaction)).thenReturn(false);
        when(rule2.apply(mockTransaction)).thenReturn(true);
        boolean result = fraudDetectionService.detectFraud(mockTransaction);
        assertTrue(result, "The service should detect the transaction as fraudulent.");
        verify(rule1, times(1)).apply(mockTransaction);
        verify(rule2, times(1)).apply(mockTransaction);
    }

    @Test
    void testDetectFraud_NonFraudulentTransaction() {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setAccountId("acc124");
        mockTransaction.setAmount(50.0);
        mockTransaction.setTransactionTime(1672956423671L);
        mockTransaction.setTransactionId("tx124");
        when(rule1.apply(mockTransaction)).thenReturn(false);
        when(rule2.apply(mockTransaction)).thenReturn(false);
        boolean result = fraudDetectionService.detectFraud(mockTransaction);
        assertFalse(result, "The service should not detect the transaction as fraudulent.");
        verify(rule1, times(1)).apply(mockTransaction);
        verify(rule2, times(1)).apply(mockTransaction);
    }

    @Test
    void testDetectFraud_EmptyRules() {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setAccountId("acc125");
        mockTransaction.setAmount(500.0);
        mockTransaction.setTransactionTime(1672956423671L);
        mockTransaction.setTransactionId("tx125");
        fraudDetectionService = new FraudDetectionService(new ArrayList<>());
        boolean result = fraudDetectionService.detectFraud(mockTransaction);
        assertFalse(result, "The service should not detect fraud when there are no rules.");
    }
}
