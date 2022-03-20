package budget;
import java.io.*;
import java.util.*;

public class PurchaseList {
    enum PurchaseState {
        FOOD(1),
        CLOTHES(2),
        ENTERTAINMENT(3),
        OTHER(4),
        ALL(5),
        BACK(6);
        final int input;
        PurchaseState(int choice) {
            this.input = choice;
        }

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    private PurchaseState purchaseMenu;
    private ArrayList<Item> purchaseList = new ArrayList<>();
    private double balance = 0;
    private double purchaseTotal = 0;

    public double getBalance() {
        return balance;
    }

    public double getPurchaseTotal() {
        return purchaseTotal;
    }

    public ArrayList<Item> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseState(int input) {
        for (PurchaseState choice : PurchaseState.values()) {
            if (choice.input == input) {
                purchaseMenu = choice;
            }
        }
    }

    public String getPurchaseState() {
        return purchaseMenu.toString();
    }

    public void printPurchaseMenu() {
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
    }

    public void printSortCategoryMenu() {
        System.out.println("""
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                """);
    }

    public void listPurchases() {

        switch (purchaseMenu){
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
            case ALL -> printPurchaseList();
        }
    }

    public void savePurchases(File saveFile) {
        try (PrintWriter writer = new PrintWriter(saveFile)){
            writer.printf("%s\n", balance);
            writer.printf("%s\n", purchaseTotal);
            purchaseList.forEach(item -> writer.printf("%s,%s,%s\n",
                    item.getItemName(), item.getItemCost(), item.getCategory().name()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadPurchases(File saveFile) {
        try {
            Scanner reader = new Scanner(saveFile);
            balance = Double.parseDouble(reader.nextLine());
            purchaseTotal = Double.parseDouble(reader.nextLine());
            while (reader.hasNext()) {
                String[] itemLine = reader.nextLine().split(",");
                purchaseList.add(new Item(itemLine[0], itemLine[1], itemLine[2]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addIncome(double income) {
        balance += income;
    }

    public void addPurchase(Item newItem) {
        purchaseList.add(newItem);
        purchaseTotal += newItem.getItemCost();
        balance -= newItem.getItemCost();
    }

    public void printPurchaseList(ArrayList<Item> list) {
        double total = 0;
        for (Item item : list) {
            System.out.println(item);
            total += item.getItemCost();
        }
        System.out.printf("Total sum: $%.02f\n\n", total);
    }

    public void printPurchaseList() {
        purchaseList.forEach(System.out::println);
        System.out.printf("Total sum: $%.2f\n\n", purchaseTotal);
    }
}