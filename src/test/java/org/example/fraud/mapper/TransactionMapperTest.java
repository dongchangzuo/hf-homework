package org.example.fraud.mapper;

import org.example.fraud.model.Transaction;
import org.example.fraud.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.profiles.active=test")
@Transactional
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionService transactionService;


    @Test
    void testInsertTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("a2f5bc42fbe7423e93f0212f85a6aca6");
        transaction.setAccountId("a2f5bc42fbe7423e93f0212f85a6acb5");
        transaction.setAmount(100.0);
        transaction.setTransactionTime(System.currentTimeMillis());
        transactionMapper.insertTransaction(transaction);
    }

    @Test
    void testCountTransactionsInTimeRange() {
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId("a2f5bc42fbe7423e93f0212f85a1aca6");
        transaction1.setAccountId("a2f5bc42fbe7423e93f0212f85a6acb6");
        transaction1.setAmount(100.0);
        transaction1.setTransactionTime(System.currentTimeMillis());
        transactionMapper.insertTransaction(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId("a2f5bc42fbe7423e93f0212f85a2aca6");
        transaction2.setAccountId("a2f5bc42fbe7423e93f0212f85a6acb6");
        transaction2.setAmount(200.0);
        transaction2.setTransactionTime(System.currentTimeMillis());
        transactionMapper.insertTransaction(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setTransactionId("a2f5bc42fbe7423e93f0212f85a3aca6");
        transaction3.setAccountId("a2f5bc42fbe7423e93f0212f85a6acb6");
        transaction3.setAmount(300.0);
        transaction3.setTransactionTime(System.currentTimeMillis());
        transactionMapper.insertTransaction(transaction3);

        long currentTime = System.currentTimeMillis();
        long startTime = currentTime - (5 * 60 * 1000);

        int transactionCount = transactionMapper.countTransactionsInTimeRange("a2f5bc42fbe7423e93f0212f85a6acb6", startTime, currentTime);
        assertThat(transactionCount).isEqualTo(3);
    }
}
