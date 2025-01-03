package org.example.fraud.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void testAccount() {
        Account account = new Account("id", "suspiciousStatus");
        account.setId("id");
        account.setSuspiciousStatus("suspiciousStatus");
        Assertions.assertEquals("id", account.getId());
        Assertions.assertEquals("suspiciousStatus", account.getSuspiciousStatus());
        Assertions.assertEquals("Account{id='id', suspiciousStatus=suspiciousStatus}", account.toString());
    }
}
