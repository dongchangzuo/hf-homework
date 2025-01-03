package org.example.fraud.service;

import org.example.fraud.model.Transaction;
import org.example.fraud.rule.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudDetectionService {

    private final List<Rule> fraudRules;


    @Autowired
    public FraudDetectionService(List<Rule> fraudRules) {
        this.fraudRules = fraudRules;
    }

    public boolean detectFraud(Transaction transaction) {
        for (Rule rule : fraudRules) {
            if (rule.apply(transaction)) {
                return true;
            }
        }
        return false;
    }
}
