/**
 * This class represents the items that can be stored in a vending machine.
 */
public class Items {
    /**
     * This class represents an item that can be stored in a vending machine.
     */
    public static class Item {
        private int quantity;
        private String name;
        private double price;
        private int calories;
        private boolean isIngredient;

        /**
         * Constructs a new Item object with the specified quantity, name, price, and calories.
         * @param quantity the quantity of the item
         * @param name the name of the item
         * @param price the price of the item
         * @param calories the calories of the item
         * @param isIngredient2
         */
        public Item(int quantity, String name, double price, int calories, boolean isIngredient2) {
            this.quantity = quantity;
            this.name = name;
            this.price = price;
            this.calories = calories;
        }

        /**
         * Returns the quantity of this item.
         * @return the quantity of this item
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Sets the quantity of this item to the specified value.
         * @param quantity the new quantity of this item
         */
        public void setQuantity(int quantity) {
            this.quantity += quantity;
        }

        /**
         * Returns the name of this item.
         * @return the name of this item
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the price of this item.
         * @return the price of this item
         */
        public double getPrice() {
            return price;
        }

        /**
         * Sets the price of this item to the specified value.
         * @param price the new price of this item
         */
        public void setPrice(double price) {
            this.price = price;
        }

        /**
         * Returns the calories of this item.
         * @return the calories of this item
         */
        public int getCalories() {
            return calories;
        }

        public boolean isIngredient() {
            return isIngredient;
        }
    }

    /**
     * This class represents a slot in a vending machine that can hold an item.
     */
    public static class ItemSlot {
        private Item item;
        private int totalSold;

        /**
         * Constructs a new ItemSlot object with no item and 0 total sold.
         */
        public ItemSlot() {
            item = null;
            totalSold = 0;
        }

        /**
         * Returns the item in this slot.
         * @return the item in this slot
         */
        public Item getItem() {
            return item;
        }

        /**
         * Sets the item in this slot to the specified value.
         * @param item the new item in this slot
         */
        public void setItem(Item item) {
            this.item = item;
        }

        /**
         * Returns the total number of items sold from this slot.
         * @return the total number of items sold from this slot
         */
        public int getTotalSold() {
            return totalSold;
        }

        /**
         * Increments the total number of items sold from this slot by the specified amount.
         * @param totalSold the amount to increment by
         */
        public void setTotalSold(int totalSold) {
            this.totalSold += totalSold;
        }
    }
}