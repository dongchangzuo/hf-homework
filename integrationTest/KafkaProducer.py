from confluent_kafka import Producer
import json
import time

kafka_config = {
    'bootstrap.servers': 'kafka-service.kafaka.svc.cluster.local:9092',
    'queue.buffering.max.messages': 1000000,
    'queue.buffering.max.kbytes': 1048576,
    'queue.buffering.max.ms': 500
}

topic = 'transactions'

producer = Producer(kafka_config)

def delivery_report(err, msg):
    if err is not None:
        print(f"Message delivery failed: {err}")
    else:
        print(f"Message delivered to {msg.topic()} [{msg.partition()}]")

def read_transactions(file_path):
    with open(file_path, 'r') as file:
        return json.load(file)

def produce_messages(file_path):
    transactions = read_transactions(file_path)
    for transaction in transactions:
        message = json.dumps(transaction)
        producer.produce(topic, message.encode('utf-8'), callback=delivery_report)

    producer.flush()

if __name__ == "__main__":
    transactions_file = "transactions.json"
    print("Producing messages to Kafka...")
    produce_messages(transactions_file)
    print("Finished producing messages.")
