import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Transaction {
    String transactionType;
    int quantity;
    double price;
    LocalDate date;
    int pl;

    public Transaction(String transactionType, int quantity, double price, LocalDate date) {
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + transactionType + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}

class Asset {
    String assetID;
    String assetType;
    int quantity;
    double purchasePrice;
    double currentPrice;
    ArrayList<Transaction> transactions = new ArrayList<>();

    public Asset(String assetID, String assetType, int quantity, double purchasePrice, double currentPrice) {
        this.assetID = assetID;
        this.assetType = assetType;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
    }

    public void addQuantity(int quantity, double price) {
        this.quantity += quantity;
        this.currentPrice = price;
        transactions.add(new Transaction("buy", quantity, price, LocalDate.now()));
    }

    public double currentValue() {
        return quantity * currentPrice;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "ID='" + assetID + '\'' +
                ", Type='" + assetType + '\'' +
                ", Quantity=" + quantity +
                ", Purchase Price=" + purchasePrice +
                ", Current Price=" + currentPrice +
                ", Current Value=" + currentValue() +
                ", Transactions=" + transactions +
                '}';
    }
}

class Portfolio {
    String clientID;
    HashMap<String, Asset> assetMap = new HashMap<>();

    public Portfolio(String clientID) {
        this.clientID = clientID;
    }

    public void addAsset(String assetID, String assetType, int quantity, double purchasePrice, double currentPrice) {
        if (assetMap.containsKey(assetID)) {
            Asset asset = assetMap.get(assetID);
            asset.addQuantity(quantity, currentPrice);
            System.out.println("Asset updated: " + assetID + ", New quantity: " + asset.quantity);
        } else {
            Asset newAsset = new Asset(assetID, assetType, quantity, purchasePrice, currentPrice);
            assetMap.put(assetID, newAsset);
            System.out.println("New asset added: " + assetID);
        }
    }

    public void displayPortfolio() {
        System.out.println("Portfolio for Client ID: " + clientID);
        for (Asset asset : assetMap.values()) {
            System.out.println(asset);
        }
    }
}

class Client {
    String clientID;
    String name;
    Portfolio portfolio;

    public Client(String clientID, String name) {
        this.clientID = clientID;
        this.name = name;
        this.portfolio = new Portfolio(clientID);
    }

    public void addAssetToPortfolio(String assetID, String assetType, int quantity, double purchasePrice, double currentPrice) {
        portfolio.addAsset(assetID, assetType, quantity, purchasePrice, currentPrice);
    }

    public void displayPortfolio() {
        portfolio.displayPortfolio();
    }
}

public class WealthManagementSystem {
    HashMap<String, Client> clientMap = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    public void addClient() {
        System.out.print("Enter Client ID: ");
        String clientID = scanner.next();
        System.out.print("Enter Client Name: ");
        String name = scanner.next();
        clientMap.put(clientID, new Client(clientID, name));
        System.out.println("Client added successfully.");
    }

    public void addAssetToClient() {
        System.out.print("Enter Client ID: ");
        String clientID = scanner.next();
        Client client = clientMap.get(clientID);
        if (client != null) {
            System.out.print("Enter Asset ID: ");
            String assetID = scanner.next();
            System.out.print("Enter Asset Type: ");
            String assetType = scanner.next();
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            System.out.print("Enter Purchase Price: ");
            double purchasePrice = scanner.nextDouble();
            System.out.print("Enter Current Price: ");
            double currentPrice = scanner.nextDouble();

            client.addAssetToPortfolio(assetID, assetType, quantity, purchasePrice, currentPrice);
        } else {
            System.out.println("Client not found.");
        }
    }

    public void displayClientPortfolio() {
        System.out.print("Enter Client ID: ");
        String clientID = scanner.next();
        Client client = clientMap.get(clientID);
        if (client != null) {
            client.displayPortfolio();
        } else {
            System.out.println("Client not found.");
        }
    }

    public void start() {
        int choice;
        do {
            System.out.println("\nWealth Management System");
            System.out.println("1. Add Client");
            System.out.println("2. Add Asset to Client");
            System.out.println("3. Display Client Portfolio");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addClient();
                    break;
                case 2:
                    addAssetToClient();
                    break;
                case 3:
                    displayClientPortfolio();
                    break;
                case 4:
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public static void main(String[] args) {
        WealthManagementSystem system = new WealthManagementSystem();
        system.start();
    }
}
