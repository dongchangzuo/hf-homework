package org.example.fraud.model;


import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String suspiciousStatus;

    public Account() {
    }

    public Account(String id, String suspiciousStatus) {
        this.id = id;
        this.suspiciousStatus = suspiciousStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuspiciousStatus() {
        return suspiciousStatus;
    }

    public void setSuspiciousStatus(String suspiciousStatus) {
        this.suspiciousStatus = suspiciousStatus;
    }

    @Override
    public String toString() {
        return "Account{id='" + id + "', suspiciousStatus=" + suspiciousStatus + "}";
    }
}
