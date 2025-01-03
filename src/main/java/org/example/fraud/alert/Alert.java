package org.example.fraud.alert;

import org.example.fraud.model.Transaction;

public interface Alert {
    void sendAlert(Transaction transaction);
}
