import uuid
import random
import time
import json
import argparse

account_sql_file = "accounts.sql"
transactions_json_file = "transactions.json"
expected_json_file = "expected.json"

def generate_uuid():
    return str(uuid.uuid4()).replace("-", "")

def random_suspicious_status():
    return random.choice(["NORMAL", "SUSPICIOUS"])

def generate_accounts(num_accounts):
    accounts = []
    with open(account_sql_file, "w") as sql_file:
        sql_file.write("INSERT INTO account (id, suspicious_status) VALUES\n")
        for i in range(num_accounts):
            account_id = generate_uuid()
            suspicious_status = random_suspicious_status()
            accounts.append((account_id, suspicious_status))
            sql_file.write(f"('{account_id}', '{suspicious_status}')")
            sql_file.write(",\n" if i < num_accounts - 1 else ";\n")
    return accounts

def generate_transactions(accounts, min_transactions_per_account, max_transactions_per_account):
    all_transactions = {}
    for account_id, _ in accounts:
        num_transactions = random.randint(min_transactions_per_account, max_transactions_per_account)
        transactions = []
        last_transaction_time = int(time.time() * 1000)

        for i in range(num_transactions):
            transaction_time_increment = random.randint(1000, 60000)
            transaction_time = last_transaction_time + transaction_time_increment
            last_transaction_time = transaction_time
            transaction = {
                "transactionId": generate_uuid(),
                "accountId": account_id,
                "amount": round(random.uniform(1.0, 200000.0), 2),
                "transactionTime": transaction_time,
            }
            transactions.append(transaction)
        all_transactions[account_id] = transactions
    return all_transactions

def check_timewindow_transations_per_account(all_transactions, timewindow, max_transactions, amount_threshold, accounts):
    timewindow_in_ms = timewindow * 60 * 1000
    suspicious_transactions = []

    for account_id, transactions in all_transactions.items():
        account_suspicious_status = next(status for acc_id, status in accounts if acc_id == account_id)

        if account_suspicious_status == "SUSPICIOUS":
            suspicious_transactions.extend(transactions)
            continue

        for i, transaction in enumerate(transactions):
            if transaction["amount"] > amount_threshold:
                suspicious_transactions.append(transaction)
                continue

            transaction_time = transaction["transactionTime"]
            start_window = transaction_time - timewindow_in_ms
            count_in_window = 0
            for t in transactions[:i]:
                if start_window <= t["transactionTime"] <= transaction_time:
                    count_in_window += 1

            if count_in_window > max_transactions:
                suspicious_transactions.append(transaction)

    return suspicious_transactions

def main():
    parser = argparse.ArgumentParser(description="Generate accounts and transactions data.")
    parser.add_argument("--num_accounts", type=int, default=200, help="Number of accounts to generate")
    parser.add_argument("--min_transactions_per_account", type=int, default=1, help="Minimum transactions per account")
    parser.add_argument("--max_transactions_per_account", type=int, default=200, help="Maximum transactions per account")
    parser.add_argument("--timewindow", type=int, required=True, help="Time window in minutes")
    parser.add_argument("--max_transactions", type=int, required=True, help="Maximum number of transactions allowed in the time window")
    parser.add_argument("--amount_threshold", type=float, required=True, help="Amount threshold for suspicious transactions")

    args = parser.parse_args()

    print(f"Generating {args.num_accounts} accounts and transactions with {args.min_transactions_per_account}-{args.max_transactions_per_account} transactions per account...")
    print(f"Checking transactions in a {args.timewindow}-minute window, with a max of {args.max_transactions} transactions and amount threshold {args.amount_threshold}.")

    accounts = generate_accounts(args.num_accounts)
    all_transactions = generate_transactions(
        accounts,
        args.min_transactions_per_account,
        args.max_transactions_per_account
    )

    suspicious_transactions = check_timewindow_transations_per_account(all_transactions, args.timewindow, args.max_transactions, args.amount_threshold, accounts)

    with open(expected_json_file, "w") as json_file:
        json.dump(suspicious_transactions, json_file, indent=4)

    with open(transactions_json_file, "w") as json_file:
        json.dump(all_transactions, json_file, indent=4)

    print(f"Data generation complete! Files saved: {account_sql_file}, {transactions_json_file}, {expected_json_file}")

if __name__ == "__main__":
    main()
