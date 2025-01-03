package org.example.fraud.alert;

import org.example.fraud.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaAlert implements Alert {
    private static final Logger log = LoggerFactory.getLogger(KafkaAlert.class);


    private static final String FRAUD_ALERTS_TOPIC = "fraud-alerts";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaAlert(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendAlert(Transaction transaction) {
        String fraudAlertMessage = transaction.toJson();
        log.info("here is the fraud alert message: {}", fraudAlertMessage);
        kafkaTemplate.send(FRAUD_ALERTS_TOPIC, fraudAlertMessage);
        kafkaTemplate.flush();
    }
}
