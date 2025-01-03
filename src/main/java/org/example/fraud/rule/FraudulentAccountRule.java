package org.example.fraud.rule;

import org.example.fraud.model.Account;
import org.example.fraud.model.SuspiciousStatus;
import org.example.fraud.model.Transaction;
import org.example.fraud.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class FraudulentAccountRule implements Rule {

    private static final Logger log = LoggerFactory.getLogger(FraudulentAccountRule.class);

    private final AccountService accountService;

    @Autowired
    public FraudulentAccountRule(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean apply(Transaction transaction) {
        Account account = accountService.getAccountById(transaction.getAccountId());
        log.info("Account suspicious status: {}", account.getSuspiciousStatus());
        return SuspiciousStatus.valueOf(account.getSuspiciousStatus()) == SuspiciousStatus.SUSPICIOUS;
    }

    @Override
    public String getRuleName() {
        return "FraudulentAccountRule";
    }
}
