package org.example.fraud.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void testTransaction() {
        Transaction transaction = new Transaction("id", "accountId", 100.0, 1672956423671L);
        transaction.setTransactionId("id");
        transaction.setAccountId("accountId");
        transaction.setAmount(100.0);
        transaction.setTransactionTime(1672956423671L);
        Assertions.assertEquals("id", transaction.getTransactionId());
        Assertions.assertEquals("accountId", transaction.getAccountId());
        Assertions.assertEquals(100.0, transaction.getAmount());
        Assertions.assertEquals(1672956423671L, transaction.getTransactionTime());
        Assertions.assertEquals("Transaction{transactionId='id', accountId='accountId', amount=100.0, transactionTime=1672956423671}", transaction.toString());
        Assertions.assertEquals("{\"transactionId\":\"id\",\"accountId\":\"accountId\",\"amount\":100.0,\"transactionTime\":1672956423671}", transaction.toJson());
    }

    @Test
    public void testEquals() {
        Transaction transaction1 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Transaction transaction2 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Assertions.assertEquals(transaction1, transaction2);
    }

    @Test
    public void testNotEquals() {
        Transaction transaction1 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Transaction transaction2 = new Transaction("id2", "accountId", 100.0, 1672956423672L);
        Assertions.assertNotEquals(transaction1, transaction2);
    }

    @Test
    public void testHashCodeEquals() {
        Transaction transaction1 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Transaction transaction2 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Assertions.assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void testHashCodeNotEquals() {
        Transaction transaction1 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Transaction transaction2 = new Transaction("id2", "accountId", 100.0, 1672956423672L);
        Assertions.assertNotEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void testSameObject() {
        Transaction transaction1 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Assertions.assertEquals(transaction1, transaction1);
    }

    @Test
    public void testDifferentClass() {
        Transaction transaction1 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Assertions.assertNotEquals(new Object(), transaction1);
    }

    @Test
    public void testNull() {
        Transaction transaction1 = new Transaction("id", "accountId", 100.0, 1672956423671L);
        Assertions.assertNotEquals(transaction1, null);
    }

}
