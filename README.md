# Sprint_7

```markdown

## Описание

Этот проект разработан в рамках **7-го спринта** курса "Инженер по тестированию: от новичка до автоматизатора" на Яндекс.Практикуме

### Технологии:

- Java 11
- Maven
- JUnit 4
- RestAssured
- Allure

## Установка

1. Убедитесь, что у вас установлены [Java JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) и [Maven](https://maven.apache.org/download.cgi).
2. Склонируйте репозиторий на локальный компьютер:

   ```bash
   git clone https://github.com/mir9635/Sprint_7
   cd Sprint_7
   ```

3. Установите зависимости:

   ```bash
   mvn install
   ```

## Использование

Запустите тесты с помощью следующей команды:

```bash
mvn test
```

Сгенерируйте отчет Allure:

```bash
mvn allure:serve
```

## Структура проекта

```plaintext
Sprint_7
│
├── src
│   ├── main
│   │   └── java   
│   │       ├── api
│   │       │   ├── client
│   │       │   │   └── AuthClient.java
│   │       │   ├── courier
│   │       │   │   ├── Courier.java
│   │       │   │   └── CreatingCourier.java
│   │       │   └── orders
│   │       │       ├── Order.java
│   │       │       └── OrdersClient.java
│   │       │   
│   │       └── org
│   │           └── springpattern
│   │              
│   │   
│   └── test
│       └── java
│           └── org
│               └── springpattern
│                   ├── CourierLoginTest.java
│                   ├── CreatingCourierTest.java
│                   ├── GettingListOfOrdersTest.java
│                   └── OrderCreationTest.java
│
├── pom.xml
└── README.md
```

### pom.xml

Файл `pom.xml` содержит все зависимости проекта и параметры сборки, такие как:

- JUnit4 для тестирования
- RestAssured для работы с RESTful API
- Allure для генерации отчетов о выполнении тестов

