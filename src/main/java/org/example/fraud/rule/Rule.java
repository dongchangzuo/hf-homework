package org.example.fraud.rule;

import org.example.fraud.model.Transaction;

public interface Rule {
    boolean apply(Transaction transaction);

    String getRuleName();
}
