# eBanking Application

This is a Java-based application that simulates a central banking system. It allows users to manage their portfolios, exchange currencies, transfer money, buy stocks, and upgrade to premium services.

## Features

### User Management
The application allows users to manage their portfolios. Each user is identified by their email. The `listPortfolio(String email)` method is used to list all the stocks and accounts associated with a user.

### Currency Exchange
Users can exchange one currency for another. The exchange rates are determined by the system. The `exchange(String email, String currency1, String currency2, float amount)` method is used to perform a currency exchange for a user.

### Money Transfer
Users can transfer money to other users within the system. The transfer is instant and free of charge. The `moneyTransfer(String email1, String email2, String currency, float amount)` method is used to transfer money from one user to another.

### Stock Market
Users can buy stocks from various companies. The stock prices are updated regularly. The `buyStocks(String email, String companyName, float amount)` method is used to buy stocks for a user. The `recommendStocks()` method is used to recommend stocks to buy based on the Simple Moving Average (SMA) of the stock prices.

### Premium Services
Users can upgrade to premium services for additional benefits. The `buyPremium(String email)` method is used to upgrade a user to premium services.

## How to Use the Commands

To use the commands, you need to create an instance of the `CentralApp` class and call the respective methods. Here are some examples:

```java
CentralApp app = new CentralApp();

// To list a user's portfolio
app.listPortfolio("user@example.com");

// To perform a currency exchange for a user
app.exchange("user@example.com", "USD", "EUR", 100);

// To transfer money from one user to another
app.moneyTransfer("user1@example.com", "user2@example.com", "USD", 100);

// To buy stocks for a user
app.buyStocks("user@example.com", "Company Name", 100);

// To upgrade a user to premium services
app.buyPremium("user@example.com");
