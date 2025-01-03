package org.example.fraud.mapper;

import org.example.fraud.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.profiles.active=test")
class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    void testFindById() {
        String accountId = "a2f5bc42fbe7423e93f0212f85a6acb5";
        Account actualAccount = accountMapper.findById(accountId);
        assertEquals(accountId, actualAccount.getId(), "Account ID should match");
        assertEquals("NORMAL", actualAccount.getSuspiciousStatus(), "Suspicious status should match");
    }
}
