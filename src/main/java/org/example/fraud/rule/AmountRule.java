package org.example.fraud.rule;

import org.example.fraud.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class AmountRule implements Rule {

    private static final Logger log = LoggerFactory.getLogger(AmountRule.class);

    @Value("${fraud.rules.amountRule.threshold}")
    private double thresholdAmount;

    @Override
    public boolean apply(Transaction transaction) {
        log.info("Transaction amount: {}, threshold: {}", transaction.getAmount(), thresholdAmount);
        return transaction.getAmount() > thresholdAmount;
    }

    @Override
    public String getRuleName() {
        return "AmountRule";
    }
}
