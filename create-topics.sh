#!/bin/bash

sleep 15
# Создание топиков
/opt/bitnami/kafka/bin/kafka-topics.sh \
  --bootstrap-server kafka:9092 \
  --create --if-not-exists \
  --topic user-registered --partitions 1 --replication-factor 1 && \
/opt/bitnami/kafka/bin/kafka-topics.sh \
  --bootstrap-server kafka:9092 \
  --create --if-not-exists \
  --topic sports-data-uploaded --partitions 3 --replication-factor 1 && \
/opt/bitnami/kafka/bin/kafka-topics.sh \
  --bootstrap-server kafka:9092 \
  --create --if-not-exists \
  --topic workout-completed --partitions 2 --replication-factor 1

echo "Все топики созданы"
