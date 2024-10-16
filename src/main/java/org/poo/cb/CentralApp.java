package org.poo.cb;

import org.poo.cb.Command.*;
import org.poo.cb.Exchange.*;
import org.poo.cb.UserDecorator.BasicUser;
import org.poo.cb.UserDecorator.PremiumUser;
import org.poo.cb.UserDecorator.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CentralApp {
    private static CentralApp instance;
    private HashMap<String, Float> exchangeRates;
    public HashMap<String, ExchangeStrategy> exchangeStrategies;
    private HashMap<String, ArrayList<Double>> stocks;
    private static ArrayList<String> currencies;
    private static ArrayList<User> users;
    private static HashMap<String, Command> commands;
    private CentralApp() {}

    public void reset() {
        instance = null;
    }

    public static CentralApp getInstance() {
        if (instance == null) {
            instance = new CentralApp();

            currencies = new ArrayList<>();
            currencies.add("EUR");
            currencies.add("GBP");
            currencies.add("JPY");
            currencies.add("CAD");
            currencies.add("USD");

            users = new ArrayList<>();

            instance.exchangeStrategies = new HashMap<>();
            instance.exchangeRates = new HashMap<>();
            instance.loadExchanheRates();
            instance.stocks = new HashMap<>();
            instance.loadCommands();
        }

        return instance;
    }

    public HashMap<String, Float> getExchangeRates() {
        return exchangeRates;
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    private void loadExchanheRates() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/common/exchangeRates.csv"))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String baseCurrency = data[0];

                for (int i = 1; i < data.length; i++) {
                    exchangeRates.put(baseCurrency + currencies.get(i - 1), Float.parseFloat(data[i]));

                    String strategy = baseCurrency + "->" + currencies.get(i - 1);
                    switch (strategy) {
                        case "USD->EUR" -> exchangeStrategies.put(strategy, new USDToEUR());
                        case "EUR->USD" -> exchangeStrategies.put(strategy, new EURToUSD());
                        case "EUR->GBP" -> exchangeStrategies.put(strategy, new EURToGBP());
                        case "GBP->EUR" -> exchangeStrategies.put(strategy, new GBPToEUR());
                        case "EUR->JPY" -> exchangeStrategies.put(strategy, new EURToJPY());
                        case "JPY->EUR" -> exchangeStrategies.put(strategy, new JPYToEUR());
                        case "EUR->CAD" -> exchangeStrategies.put(strategy, new EURToCAD());
                        case "CAD->EUR" -> exchangeStrategies.put(strategy, new CADToEUR());
                        case "GBP->USD" -> exchangeStrategies.put(strategy, new GBPToUSD());
                        case "USD->GBP" -> exchangeStrategies.put(strategy, new USDToGBP());
                        case "GBP->JPY" -> exchangeStrategies.put(strategy, new GBPToJPY());
                        case "JPY->GBP" -> exchangeStrategies.put(strategy, new JPYToGBP());
                        case "GBP->CAD" -> exchangeStrategies.put(strategy, new GBPToCAD());
                        case "CAD->GBP" -> exchangeStrategies.put(strategy, new CADToGBP());
                        case "JPY->USD" -> exchangeStrategies.put(strategy, new JPYToUSD());
                        case "USD->JPY" -> exchangeStrategies.put(strategy, new USDToJPY());
                        case "JPY->CAD" -> exchangeStrategies.put(strategy, new JPYToCAD());
                        case "CAD->JPY" -> exchangeStrategies.put(strategy, new CADToJPY());
                        case "CAD->USD" -> exchangeStrategies.put(strategy, new CADToUSD());
                        case "USD->CAD" -> exchangeStrategies.put(strategy, new USDToCAD());
                        default -> {
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadStocks(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String companyName = data[0];
                ArrayList<Double> prices = new ArrayList<>();

                for (int i = 1; i < data.length; i++) {
                    prices.add(Double.parseDouble(data[i]));
                }

                stocks.put(companyName, prices);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCommands() {
        commands = new HashMap<>();
        commands.put("CREATE USER", new CreateUserCommand(instance));
        commands.put("LIST USER", new ListUserCommand(instance));
        commands.put("ADD FRIEND", new AddFriendCommand(instance));
        commands.put("ADD ACCOUNT", new AddAccountCommand(instance));
        commands.put("ADD MONEY", new AddMoneyCommand(instance));
        commands.put("LIST PORTFOLIO", new ListPortfolioCommand(instance));
        commands.put("EXCHANGE MONEY", new ExchangeMoneyCommand(instance));
        commands.put("TRANSFER MONEY", new TransferMoneyCommand(instance));
        commands.put("RECOMMEND STOCKS", new RecommendStocksCommand(instance));
        commands.put("BUY STOCKS", new BuyStocksCommand(instance));
        commands.put("BUY PREMIUM", new BuyPremiumCommand(instance));
    }

    public void createUser(String[] data) {
        User user = new BasicUser(data);

        addUser(user);
    }

    private void addUser(User user) {
        for (User u : users) {
            if (u.getEmail().equals(user.getEmail())) {
                System.out.println("User with " +
                        user.getEmail() + " already exists");

                return;
            }
        }

        users.add(user);
    }

    public void listUser(String email) {
        User user = findUser(email);

        if (user == null) {
            return;
        }

        System.out.println("{\"email\":\"" + email
                + "\",\"firstname\":\"" + user.getFirstName()
                + "\",\"lastname\":\"" + user.getLastName()
                + "\",\"address\":\"" + user.getAdress()
                + "\",\"friends\":["
                + user.printFriends() + "]}");
    }

    public User findUser(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        System.out.println("User with email " + email + " doesn't exist");
        return null;
    }

    public void addFriend(String email1, String email2) {
        User user1 = findUser(email1);
        User user2 = findUser(email2);

        if (user1 == null || user2 == null)
            return;

        isFriend(user1, user2);

        user1.getFriends().add(user2);
        user2.getFriends().add(user1);
    }

    private void isFriend(User user1, User user2) {
        if (user1.getFriends().contains(user2)) {
            System.out.println("User with " + user1.getEmail() + " is already a friend");
        }
    }

    public void addAccount(String email, String currency) {
        User user = findUser(email);

        if (user == null)
            return;

        for (Portfolio portfolio : user.getPortfolio()) {
                if (portfolio.isAccount() && portfolio.getCurencyType().equals(currency)) {
                    System.out.println("Account in curency " + currency + " already exists for user");
                    return;
                }
        }

        user.getPortfolio().add(new Account(currency));
    }

    public void addMoney(String email, String currency, int amount) {
        User user = findUser(email);

        if (user == null)
            return;

        for (Portfolio portfolio : user.getPortfolio()) {
            if (portfolio.getCurencyType().equals(currency)) {
                ((Account) portfolio).setBalance(((Account) portfolio).getBalance() + amount);
                return;
            }
        }

        System.out.println("Account in curency " + currency + " does not exist for user");
    }

    public void listPortfolio(String email) {
        User user = findUser(email);
        StringBuilder result = new StringBuilder();

        if (user == null)
            return;

        result.append("{\"stocks\":[");

        for (Portfolio portfolio : user.getPortfolio()) {
            if (portfolio.isStock()) {
                result.append(portfolio.list());
            }
        }

        result.append("],\"accounts\":[");

        for (Portfolio portfolio : user.getPortfolio()) {
            if (portfolio.isAccount()) {
                result.append(portfolio.list());
            }
        }

        result.append("]}");

        System.out.println(result.toString().replaceAll("},]", "}]"));
    }

    public void exchange(String email, String currency1, String currency2, float amount) {
        User user = findUser(email);

        if (user == null)
            return;

        user.exchangeMoney(user, currency1, currency2, amount, this);
    }

    public Account findAccount(String email, String currency) {
        User user = findUser(email);

        if (user == null)
            return null;

        for (Portfolio portfolio : user.getPortfolio()) {
            if (portfolio.isAccount() && portfolio.getCurencyType().equals(currency))
                return (Account) portfolio;
        }

        return null;
    }

    public void moneyTransfer(String email1, String email2, String currency, float amount) {
        User user1 = findUser(email1);
        User user2 = findUser(email2);

        if (user1.getFriends() == null || !user1.getFriends().contains(user2)) {
            System.out.println("User with " + user2.getEmail() + " is not a friend");
            return;
        }

        Account account1 = findAccount(email1, currency);
        Account account2 = findAccount(email2, currency);

        if (account1 == null || account2 == null)
            return;

        if (amount > account1.getBalance()) {
            System.out.println("Insufficient amount in account " + currency + " for transfer");
            return;
        }

        account1.setBalance(account1.getBalance() - amount);
        account2.setBalance(account2.getBalance() + amount);
    }

    public void recommendStocks() {
        StringBuilder recommendedStocks = new StringBuilder("{\"stocksToBuy\":[");

        for (String companyName : stocks.keySet()) {
            double shortTermSMA = SMA(companyName, stocks, false);
            double longTermSMA = SMA(companyName, stocks, true);

            if (shortTermSMA < longTermSMA) {
                recommendedStocks.append("\"").append(companyName).append("\"");
            }
        }

        recommendedStocks.append("]}");

        System.out.println(recommendedStocks.toString().replaceAll("\"\"", "\",\""));
    }

    private double SMA(String companyName, HashMap<String, ArrayList<Double>> stocks, boolean isLongTerm) {
        double sum = 0;
        int count = 0;
        int max;

        if (isLongTerm) {
            max = 10;
        } else {
            max = 5;
        }

        for (double price : stocks.get(companyName)) {
            if (count < max) {
                sum += price;
                count++;
            }
        }

        return sum / count;
    }

    public void buyStocks(String email, String companyName, float amount) {
        User user = findUser(email);

        if (user == null)
            return;

        user.buySomeStocks(user, companyName, amount, this);
    }

    public StockMarket findStock(String companyName, User user) {
        for (Portfolio portfolio : user.getPortfolio()) {
            if (portfolio.isStock() && portfolio.getCompanyName().equals(companyName)) {
                return (StockMarket) portfolio;
            }
        }

        for (String company : stocks.keySet()) {
            if (company.equals(companyName)) {
                return new StockMarket(companyName, stocks.get(companyName));
            }
        }

        return null;
    }

    public void buyPremium(String email) {
        User user = findUser(email);

        if (user == null) {
            System.out.println("User with email " + email + " doesn't exist");
            return;
        }

        Account account = findAccount(email, "USD");

        if (account.getBalance() < 100) {
            System.out.println("Insufficient amount in account for buying premium option");
            return;
        }

        account.setBalance(account.getBalance() - 100);

        users.set(users.indexOf(user), new PremiumUser((user)));
    }
}
