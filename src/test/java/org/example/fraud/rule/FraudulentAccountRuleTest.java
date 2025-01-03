package org.example.fraud.rule;

import org.example.fraud.model.Account;
import org.example.fraud.model.SuspiciousStatus;
import org.example.fraud.model.Transaction;
import org.example.fraud.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class FraudulentAccountRuleTest {

    private AccountService accountService;
    private FraudulentAccountRule fraudulentAccountRule;

    @BeforeEach
    void setUp() {
        accountService = Mockito.mock(AccountService.class);
        fraudulentAccountRule = new FraudulentAccountRule(accountService);
    }

    @Test
    void testApply_SuspiciousAccount() {
        Account mockAccount = new Account();
        mockAccount.setId("acc123");
        mockAccount.setSuspiciousStatus(SuspiciousStatus.SUSPICIOUS.name());
        Transaction mockTransaction = new Transaction();
        mockTransaction.setAccountId("acc123");
        mockTransaction.setAmount(5000.0);
        mockTransaction.setTransactionId("tx123");
        mockTransaction.setTransactionTime(1672956423671L);
        Mockito.when(accountService.getAccountById(anyString())).thenReturn(mockAccount);
        boolean result = fraudulentAccountRule.apply(mockTransaction);
        assertTrue(result, "The rule should detect the account as suspicious.");
    }

    @Test
    void testApply_NonSuspiciousAccount() {
        Account mockAccount = new Account();
        mockAccount.setId("acc124");
        mockAccount.setSuspiciousStatus(SuspiciousStatus.NORMAL.name());
        Transaction mockTransaction = new Transaction();
        mockTransaction.setAccountId("acc124");
        mockTransaction.setAmount(100.0);
        mockTransaction.setTransactionId("tx124");
        mockTransaction.setTransactionTime(1672956423671L);
        Mockito.when(accountService.getAccountById(anyString())).thenReturn(mockAccount);
        boolean result = fraudulentAccountRule.apply(mockTransaction);
        assertFalse(result, "The rule should not detect the account as suspicious.");
    }

    @Test
    void testGetRuleName() {
        String ruleName = fraudulentAccountRule.getRuleName();
        assertEquals("FraudulentAccountRule", ruleName, "The rule name should match.");
    }
}
