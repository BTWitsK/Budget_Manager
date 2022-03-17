package budget;

class Item {
    private final String itemName;
    private final double itemCost;

    public Item(String name, double cost) {
        this.itemName = name;
        this.itemCost = cost;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", this.itemName, this.itemCost);
    }
}
