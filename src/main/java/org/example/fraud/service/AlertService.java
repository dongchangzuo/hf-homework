package org.example.fraud.service;

import org.example.fraud.alert.Alert;
import org.example.fraud.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final List<Alert> alerts;

    @Autowired
    public AlertService(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public void sendAlerts(Transaction transaction) {
        for (Alert alert : alerts) {
            alert.sendAlert(transaction);
        }
    }
}
