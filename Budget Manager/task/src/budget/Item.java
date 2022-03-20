package budget;

class Item {
    enum Category {
        FOOD(1),
        CLOTHES(2),
        ENTERTAINMENT(3),
        OTHER(4);

        final int input;

        Category(int choice) {
            this.input = choice;
        }

        @Override
        public String toString() {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase();
        }
    }
    private final String itemName;
    private final double itemCost;
    private final Category category;

    public Item(String name, double cost, int category) {
        this.itemName = name;
        this.itemCost = cost;
        this.category = setCategory(category);
    }

    public Item(String name, String cost, String category) {
        this.itemName = name;
        this.itemCost = Double.parseDouble(cost);
        this.category = Category.valueOf(category);
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", this.itemName, this.itemCost);
    }

    public Category setCategory(int choice) {
        for (Category option : Category.values()) {
            if (option.input == choice) {
                return option;
            }
        }
        return null;
    }
    public Category getCategory() {
        return category;
    }
    public String getItemName() {
        return this.itemName;
    }
    public double getItemCost() {
        return this.itemCost;
    }
}