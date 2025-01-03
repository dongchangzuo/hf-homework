package org.example.fraud.service;

import org.example.fraud.mapper.AccountMapper;
import org.example.fraud.model.Account;
import org.example.fraud.model.SuspiciousStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;

class AccountServiceTest {

    private AccountMapper accountMapper;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountMapper = Mockito.mock(AccountMapper.class);
        accountService = new AccountService(accountMapper);
    }

    @Test
    void testGetAccountById_ValidId() {
        Account mockAccount = new Account();
        mockAccount.setId("acc123");
        mockAccount.setSuspiciousStatus(String.valueOf(SuspiciousStatus.NORMAL));
        Mockito.when(accountMapper.findById(anyString())).thenReturn(mockAccount);
        Account result = accountService.getAccountById("acc123");
        assertEquals("acc123", result.getId(), "The account ID should match.");
        assertEquals(String.valueOf(SuspiciousStatus.NORMAL), result.getSuspiciousStatus(), "The account status should match.");
    }

    @Test
    void testGetAccountById_InvalidId() {
        Mockito.when(accountMapper.findById(anyString())).thenReturn(null);
        Account result = accountService.getAccountById("invalid_id");
        assertNull(result, "The result should be null for an invalid ID.");
    }
}
