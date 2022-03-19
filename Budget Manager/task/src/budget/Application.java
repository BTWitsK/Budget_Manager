package budget;
import java.util.*;
import java.io.*;

public class Application {
    enum Menu {
        INCOME(1),
        PURCHASE(2),
        LIST(3),
        BALANCE(4),
        SAVE(5),
        LOAD(6),
        EXIT(0);
        final int userInput;
        Menu(int input) {
            this.userInput = input;
        }
    }

    enum PurchaseMenu {
        FOOD(1),
        CLOTHES(2),
        ENTERTAINMENT(3),
        OTHER(4),
        ALL(5),
        BACK(6);
        final int input;
        PurchaseMenu(int choice) {
            this.input = choice;
        }

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    private static Scanner scanner;
    private PrintWriter writer;
    private Scanner reader;
    private File saveFile;
    private Menu state;
    private PurchaseMenu purchaseMenu;
    private ArrayList<Item> purchaseList;
    private double balance;
    private double purchaseTotal;

    public Application() {
        scanner = new Scanner(System.in);
        this.purchaseList = new ArrayList<>();
        this.balance = 0.00;
        purchaseTotal = 0.00;
        saveFile = new File("purchases.txt");
    }

    public void setState(int input) {
        for (Menu choice : Menu.values()) {
            if (choice.userInput == input) {
                state = choice;
            }
        }
    }

    public Menu getState() {
        return state;
    }

    public int printMenu() {
        String output = """
                Choose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                0) Exit
                """;
        System.out.println(output);
        return Integer.parseInt(scanner.nextLine());
    }

    public void setPurchaseMenu(int input) {
        for (PurchaseMenu choice : PurchaseMenu.values()) {
            if (choice.input == input) {
                purchaseMenu = choice;
            }
        }
    }

    public int printPurchaseMenu() {
        String output = """
                Choose the type of purchases
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) All
                6) Back
                """;
        System.out.println(output);
        return Integer.parseInt(scanner.nextLine());
    }

    public int printCategories() {
        String output = """
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) Back
                """;
        System.out.println(output);
        return Integer.parseInt(scanner.nextLine());
    }

    public void addIncome() {
        System.out.println("Enter income:");
        this.balance += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!\n");
    }

    public void addPurchase() {
        int category;
        do {
            category = printCategories();
            if (category != 5) {
                System.out.println("Enter purchase name:");
                String name = scanner.nextLine();
                System.out.println("Enter its price:");
                double price = Double.parseDouble(scanner.nextLine());

                purchaseList.add(new Item(name, price, category));
                purchaseTotal += price;
                balance -= price;
                System.out.println("Purchase was added!\n");
            }
        } while (category != 5);
    }

    public void listPurchases() {
        do {
            setPurchaseMenu(printPurchaseMenu());
            if (!purchaseMenu.toString().equals("Back")) {
                System.out.printf("%s:\n", purchaseMenu.toString());

                switch (purchaseMenu) {
                    case FOOD, CLOTHES, ENTERTAINMENT, OTHER -> {
                        ArrayList<Item> tempList = new ArrayList<>();
                        purchaseList.forEach(item -> {
                            if (item.getCategory().name().equals(purchaseMenu.name())) tempList.add(item);
                        });

                        if (tempList.isEmpty()) {
                            System.out.println("The purchase list is empty!\n");
                        } else {
                            printPurchaseList(tempList);
                        }
                    }
                    case ALL -> printPurchaseList(purchaseList, purchaseTotal);
                }
            }
        } while (purchaseMenu != PurchaseMenu.BACK);
    }

    public void savePurchases() {
        try {
            writer = new PrintWriter(saveFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        writer.println(balance);
        writer.println(purchaseTotal);
        purchaseList.forEach(item -> writer.printf("%s,%s,%s\n",
                item.getItemName(), item.getItemCost(), item.getCategory().name()));
        System.out.println("Purchases were saved!");
    }

    public void loadPurchases() {
        try {
            reader = new Scanner(saveFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.balance = Double.parseDouble(reader.nextLine());
        this.purchaseTotal = Double.parseDouble(reader.nextLine());
        while (reader.hasNext()) {
            String[] itemLine = reader.nextLine().split(",");
            purchaseList.add(new Item(itemLine[0], itemLine[1], itemLine[2]));
        }
    }
    public void printPurchaseList(ArrayList<Item> list, double total) {
        list.forEach(System.out::println);
        System.out.printf("Total sum: $%.2f\n\n", total);
    }

    public void printPurchaseList(ArrayList<Item> list) {
        double total = 0;
        for (Item item : list) {
            System.out.println(item);
            total += item.getItemCost();
        }
        System.out.printf("Total sum: $%.02f\n\n", total);
    }

    public void printBalance() {
        System.out.printf("Balance: $%.02f\n\n", this.balance);
    }
}