#!/usr/bin/env bash

here=$(dirname "$0")

cd "$here/.."

./usr/local/opt/elasticsearch@5.6/bin/elasticsearch &
ELASTIC_SEARCH_PID=$!
echo "ELASTIC_SEARCH_PID = $ELASTIC_SEARCH_PID"

zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties &
ZOOKEEPER_PID=$!
echo "ZOOKEEPER_PID = $ZOOKEEPER_PID"

kafka-server-start /usr/local/etc/kafka/server.properties &
KAFKA_PID=$!
echo "KAFKA_PID = $KAFKA_PID"

kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic pocket-application

read -r -d '' _ </dev/tty

echo "Killing processes..."
kill ELASTIC_SEARCH_PID
kill ZOOKEEPER_PID
kill KAFKA_PID
echo "Processes killed"
