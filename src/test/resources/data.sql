CREATE TABLE IF NOT EXISTS account (
    id VARCHAR(32) PRIMARY KEY,
    suspicious_status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id VARCHAR(50) NOT NULL,
    account_id VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_time BIGINT NOT NULL,
    PRIMARY KEY (transaction_id)
);


DELETE FROM account;
DELETE FROM transactions;

INSERT INTO account (id, suspicious_status) VALUES ('a2f5bc42fbe7423e93f0212f85a6acb5', 'NORMAL');
INSERT INTO account (id, suspicious_status) VALUES ('a2f5bc42fbe7423e93f0212f85a6acb6', 'NORMAL');
