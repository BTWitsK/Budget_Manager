package budget;
import java.util.*;

public class Application {
    enum Menu {
        INCOME(1),
        PURCHASE(2),
        LIST(3),
        BALANCE(4),
        EXIT(0);

        final int userInput;

        Menu(int input) {
            this.userInput = input;
        }
    }

    private static Scanner scanner;
    private ArrayList<Item> purchaseList;
    private double balance;
    private double purchaseTotal;
    private Menu state;

    public Application() {
        scanner = new Scanner(System.in);
        this.purchaseList = new ArrayList<>();
        this.balance = 0.00;
        purchaseTotal = 0.00;
    }

    public void setMenu(int input) {
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
        int returnVal = Integer.parseInt(scanner.nextLine());
        System.out.println();
        return returnVal;
    }

    public void addIncome() {
        System.out.println("Enter income:");
        this.balance += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!\n");
    }

    public void addPurchase() {
        System.out.println("Enter purchase name:");
        String name = scanner.nextLine();
        System.out.println("Enter its price:");
        double price = Double.parseDouble(scanner.nextLine());

        purchaseList.add(new Item(name, price));
        purchaseTotal += price;
        balance -= price;
        System.out.println("Purchase was added!\n");
    }

    public void listPurchases() {
        if (purchaseList.isEmpty()) {
            System.out.println("The purchase list is empty\n");
        } else {
            purchaseList.forEach(System.out::println);
            System.out.printf("Total sum: $%.2f\n\n", purchaseTotal);
        }
    }

    public void printBalance() {
        System.out.printf("Balance: $%.02f\n\n", this.balance);
    }
}