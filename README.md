# Проект: Система управления задачами (Java/PostgreSQL)

## 📌 Описание проекта
Консольное приложение для управления проектами, интеграциями задачами и пользователями с использованием Java и PostgreSQL. Реализованы все CRUD-операции, многослойная архитектура и безопасное подключение к БД.

## 🛠 Технологический стек
- **Java 21** (LTS версия)
- **PostgreSQL 17** (СУБД)
- **JDBC** (для подключения к БД)
- **Docker** (для контейнеризации БД)
- **Maven** (для сборки проекта)

## 📋 Требования
- Установленная Java 21 или новее
- Docker (для запуска PostgreSQL)
- Maven 3.8+ (для сборки)

## 🚀 Запуск проекта

### 1. Подготовка базы данных
```bash
# Запуск PostgreSQL в Docker
docker run --name task-manager-db \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=tracker_db \
  -p 5432:5432 \
  -d postgres:latest
```

