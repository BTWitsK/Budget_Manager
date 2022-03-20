package budget;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();

        do {
            app.setState(app.printMenu());

            switch (app.getState()) {
                case INCOME -> app.addIncome();
                case PURCHASE -> app.addPurchase();
                case LIST -> app.listPurchases();
                case BALANCE -> app.printBalance();
                case SAVE -> app.savePurchases();
                case LOAD -> app.loadPurchases();
                case SORT -> app.sortPurchases();
                case EXIT -> System.out.print("Bye!");
            }
        } while (app.getState() != Application.Menu.EXIT);
    }
}
