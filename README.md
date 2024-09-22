# Wallet Management API

## Descrição

Este projeto é uma API de gestão de carteiras (wallets) desenvolvida com Spring Boot, Liquibase e Kafka. O objetivo é gerenciar transações financeiras de usuários, permitindo registrar transações, consultar saldos de carteiras e enviar notificações em tempo real via Kafka.

## Requisitos

- **Java 21**
- **Docker**

## Como Rodar

1. **Inicie o Docker para iniciar o PostgreSQL**:
   `docker compose up`

2. **Inicie o backend**:

## APIs

### 1. **Registrar Transação**

- **Documentação Swagger**: `http://localhost:8080/swagger-ui/index.html#/`
- **URL**: `/transactions`
- **Método**: `POST`
- **Body**:
  {
  "value": 500.0,
  "payer": 2,
  "payee": 1
  }

### 2. **Consultar Saldo da Carteira de um Usuário**

- **URL**: `/users/{id}/balance`
- **Método**: `GET`