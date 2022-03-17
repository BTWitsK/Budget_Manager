package budget;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();

        do {
            app.setMenu(app.printMenu());

            switch (app.getState()) {
                case INCOME -> app.addIncome();
                case PURCHASE -> app.addPurchase();
                case LIST -> app.listPurchases();
                case BALANCE -> app.printBalance();
                case EXIT -> System.out.print("Bye!");
            }
        } while (app.getState() != Application.Menu.EXIT);
    }
}