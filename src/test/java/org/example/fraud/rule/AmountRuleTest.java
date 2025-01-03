package org.example.fraud.rule;

import org.example.fraud.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class AmountRuleTest {

    private AmountRule amountRule;

    @BeforeEach
    void setUp() {
        amountRule = new AmountRule();
        ReflectionTestUtils.setField(amountRule, "thresholdAmount", 1000.0);
    }

    @Test
    void testApply_AmountExceedsThreshold() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("tx123");
        transaction.setAccountId("acc123");
        transaction.setAmount(1500.0);
        transaction.setTransactionTime(1672956423671L);
        boolean result = amountRule.apply(transaction);
        assertTrue(result, "The rule should detect the transaction as exceeding the threshold.");
    }

    @Test
    void testApply_AmountBelowThreshold() {
        Transaction transaction = new Transaction();
        transaction.setAmount(500.0);
        transaction.setTransactionId("tx124");
        transaction.setAccountId("acc124");
        transaction.setTransactionTime(1672956423671L);
        boolean result = amountRule.apply(transaction);
        assertFalse(result, "The rule should detect the transaction as not exceeding the threshold.");
    }

    @Test
    void testApply_AmountEqualsThreshold() {
        Transaction transaction = new Transaction();
        transaction.setAmount(1000.0);
        transaction.setAccountId("acc125");
        transaction.setTransactionId("tx125");
        transaction.setTransactionTime(1672956423671L);
        boolean result = amountRule.apply(transaction);
        assertFalse(result, "The rule should detect the transaction as not exceeding the threshold when equal.");
    }

    @Test
    void testGetRuleName() {
        String ruleName = amountRule.getRuleName();
        assertEquals("AmountRule", ruleName, "The rule name should match.");
    }
}
