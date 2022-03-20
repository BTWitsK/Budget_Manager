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
        SORT(7),
        EXIT(0);
        final int userInput;
        Menu(int input) {
            this.userInput = input;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);
    private final File saveFile = new File("purchases.txt");
    private Menu state;
    private final PurchaseList purchaseList = new PurchaseList();

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
                5) Save
                6) Load
                7) Analyze (Sort)
                0) Exit
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

    public void printSortTypes() {
        System.out.println("""
                
                How do you want to sort?
                1) sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back
                """);
    }

    public void addIncome() {
        System.out.println("Enter income:");
        purchaseList.addIncome(Double.parseDouble(scanner.nextLine()));
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

                purchaseList.addPurchase(new Item(name, price, category));
                System.out.println("Purchase was added!\n");
            }
        } while (category != 5);
    }

    public void listPurchases() {
        do {
            purchaseList.printPurchaseMenu();
            purchaseList.setPurchaseState(Integer.parseInt(scanner.nextLine()));
            if (!purchaseList.getPurchaseState().equals("Back")) {
                System.out.printf("%s:\n", purchaseList.getPurchaseState());
                purchaseList.listPurchases();
            }
        } while (!purchaseList.getPurchaseState().equals("Back"));
    }

    public void savePurchases() {
        purchaseList.savePurchases(saveFile);
        System.out.println("Purchases were saved!");
    }

    public void loadPurchases() {
        purchaseList.loadPurchases(saveFile);
        System.out.println("Purchases were loaded\n");
    }

    public Sorter executeSort(Sorter sorter) {
        sorter.sort(purchaseList);
        printSortTypes();
        return new Sorter(Integer.parseInt(scanner.nextLine()));
    }

    public void sortPurchases() {
        printSortTypes();
        Sorter sorter = new Sorter(Integer.parseInt(scanner.nextLine()));

        do {
            switch (sorter.getSortType()) {
                case ALL -> {
                    sorter.setSortingStrategy(new sortAll());
                    sorter = executeSort(sorter);
                }
                case TYPE -> {
                    sorter.setSortingStrategy(new sortByType());
                    sorter = executeSort(sorter);
                }
                case CATEGORY -> {
                    purchaseList.printSortCategoryMenu();
                    purchaseList.setPurchaseState(Integer.parseInt(scanner.nextLine()));
                    sorter.setSortingStrategy(new sortCertainType());
                    sorter = executeSort(sorter);
                }
            }
        } while (sorter.getSortType() != Sorter.Sort.BACK);

    }

    public void printBalance() {
        System.out.printf("Balance: $%.02f\n\n", purchaseList.getBalance());
    }
}