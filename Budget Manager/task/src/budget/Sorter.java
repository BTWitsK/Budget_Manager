package budget;
import java.util.*;

public class Sorter {
    enum Sort {
        ALL(1),
        TYPE(2),
        CATEGORY(3),
        BACK(4);
        final int choice;

        Sort(int choice) {
            this.choice = choice;
        }
    }

    private Sort sortType;
    private sortingStrategy sortingStrategy;

    public Sorter(int choice) {
        setSortType(choice);
    }

    public void setSortingStrategy(sortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public void sort(PurchaseList purchaseList) {
        this.sortingStrategy.sort(purchaseList);
    }

    public void setSortType(int choice) {
        for (Sort type : Sort.values()) {
            if (type.choice == choice) {
                this.sortType = type;
            }
        }
    }

    public Sort getSortType() {
        return sortType;
    }

}

interface sortingStrategy {
    void sort(PurchaseList purchaseList);
}

class sortAll implements sortingStrategy {
    @Override
    public void sort(PurchaseList purchaseList){
        if (purchaseList.getPurchaseList().isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        } else {
            System.out.println("All:");
            purchaseList.getPurchaseList().sort(Comparator.comparing(Item::getItemCost).reversed());
            purchaseList.printPurchaseList();
        }
    }
}

class sortByType implements sortingStrategy {
    @Override
    public void sort(PurchaseList purchaseList){
        HashMap<String, Double> purchaseMap = new HashMap<>();
        purchaseMap.put("Food", (double) 0);
        purchaseMap.put("Entertainment", (double) 0);
        purchaseMap.put("Clothes", (double) 0);
        purchaseMap.put("Other", (double) 0);

        if (!purchaseList.getPurchaseList().isEmpty()) {
            purchaseList.getPurchaseList().forEach(item ->
                    purchaseMap.merge(item.getCategory().toString(), item.getItemCost(), Double::sum));
            ArrayList<Map.Entry<String, Double>> entryList = new ArrayList<>(purchaseMap.entrySet());
            entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            System.out.println("\nTypes:");
            entryList.forEach(entry -> System.out.printf("%s - $%.02f\n", entry.getKey(), entry.getValue()));
            System.out.printf("Total sum: $%.02f\n", purchaseList.getPurchaseTotal());
        } else {
            System.out.println("\nTypes:");
            purchaseMap.forEach((k, v) -> System.out.printf("%s - $0\n", k));
            System.out.println("Total sum: $0\n");
        }


    }
}

class sortCertainType implements sortingStrategy {
    @Override
    public void sort(PurchaseList purchaseList) {
        if (purchaseList.getPurchaseList().isEmpty()) {
            System.out.println("The purchase list is empty!\n");
        } else {
            switch (purchaseList.getPurchaseState()) {
                case "Food", "Clothes", "Entertainment", "Other" -> {
                    ArrayList<Item> tempList = new ArrayList<>();
                    purchaseList.getPurchaseList().forEach(item -> {
                        if (item.getCategory().name().equals(purchaseList.getPurchaseState().toUpperCase())) {
                            tempList.add(item);
                        }
                    });

                    if (tempList.isEmpty()) {
                        System.out.println("The purchase list is empty!\n");
                    } else {
                        System.out.printf("%s:\n", purchaseList.getPurchaseState());
                        purchaseList.printPurchaseList(tempList);
                    }
                }
            }
        }
    }
}