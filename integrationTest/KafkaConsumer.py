import signal
import sys
import json
from kafka import KafkaConsumer

KAFKA_BROKER = 'kafka-service.kafaka.svc.cluster.local:9092'
KAFKA_TOPIC = 'fraud-alerts'
GROUP_ID = 'consumer-group-uuid'
EXPECTED_JSON_FILE = 'expected.json'

consumer = None
expected_data = {}

def load_expected_data():
    global expected_data
    try:
        with open(EXPECTED_JSON_FILE, 'r') as file:
            expected_data = json.load(file)
            print(f"Loaded {len(expected_data)} records from {EXPECTED_JSON_FILE}.")
    except FileNotFoundError:
        print(f"Error: {EXPECTED_JSON_FILE} not found.")
        sys.exit(1)
    except json.JSONDecodeError:
        print(f"Error: Failed to decode {EXPECTED_JSON_FILE}.")
        sys.exit(1)

def consume_messages():
    global consumer
    consumer = KafkaConsumer(
        KAFKA_TOPIC,
        bootstrap_servers=KAFKA_BROKER,
        group_id=GROUP_ID,
        value_deserializer=lambda x: json.loads(x.decode('utf-8')),
        auto_offset_reset='earliest',
        enable_auto_commit=True
    )

    print(f"Listening to topic: {KAFKA_TOPIC}")
    for message in consumer:
        print
        if not check_message_in_expected(message.value):
            print(f"Message not found in expected.json: {message.value}")

def check_message_in_expected(message):
    transaction_id = message.get('transactionId')
    if transaction_id and transaction_id in expected_data:
        return True
    return False

def signal_handler(sig, frame):
    print("\nStopping consumer...")
    if consumer:
        consumer.close()
    sys.exit(0)

if __name__ == "__main__":
    signal.signal(signal.SIGINT, signal_handler)
    load_expected_data()
    consume_messages()
