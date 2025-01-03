package org.example.fraud.model;


import java.io.Serializable;
import java.util.Objects;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String accountId;
    private double amount;
    private long transactionTime;

    public Transaction() {
    }

    public Transaction(String transactionId, String accountId, double amount, long transactionTime) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionTime = transactionTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(long transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public String toString() {
        return "Transaction{transactionId='" + transactionId + "', accountId='" + accountId + "', amount=" + amount + ", transactionTime=" + transactionTime + "}";
    }

    public String toJson() {
        return "{\"transactionId\":\"" + transactionId + "\",\"accountId\":\"" + accountId + "\",\"amount\":" + amount + ",\"transactionTime\":" + transactionTime + "}";
    }

    public int hashCode() {
        return Objects.hash(transactionId);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId);

    }

}