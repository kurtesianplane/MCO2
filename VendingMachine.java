<<<<<<< HEAD
/**
 * The VendingMachine class represents a vending machine that dispenses items and accepts payments.
 * It contains an array of item slots, tracks the total money inserted, money earned, and available change denominations.
 */
public class VendingMachine {
    /**
     * Array of available denominations for change.
     */
    public static final int[] DENOMINATIONS = {1, 5, 10, 20, 50, 100};

    /**
     * Array of item slots to hold items available for purchase.
     */
    protected Items.ItemSlot[] itemSlots = new Items.ItemSlot[30];

    /**
     * Total money currently present in the vending machine.
     */
    public double moneyTotal;

    /**
     * Total money earned from item sales.
     */
    public double moneyEarned;

    /**
     * Array to track the available denominations for change.
     */
    public int[] changeDenominations;

    /**
     * Constructs a new VendingMachine object with default initial values.
     * The vending machine will have 10 item slots, each initialized to an empty ItemSlot.
     * The moneyTotal and moneyEarned will be set to 0, and the changeDenominations array will be initialized with 0 values.
     */
    public VendingMachine() {
        itemSlots = new Items.ItemSlot[10];
        for (int i = 0; i < itemSlots.length; i++) {
            itemSlots[i] = new Items.ItemSlot();
        }
        moneyTotal = 0;
        moneyEarned = 0;
        changeDenominations = new int[DENOMINATIONS.length];
    }

    /**
     * Adds an item to the vending machine's item slots.
     * If an empty slot is available, the item will be added.
     * If all slots are occupied, the item will not be added.
     *
     * @param name          The name of the item to add.
     * @param price         The price of the item to add.
     * @param calories      The number of calories in the item.
     * @param imageFilename The filename of the image associated with the item.
     */
    public void addItem(String name, double price, int calories, String imageFilename) {
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            if (slot.getItem() == null) {
                Items.Item item = new Items.Item(0, name, price, calories, false, Items.Item.ItemType.MILK_TEA, null);
                slot.setItem(item);
                break;
            }
        }
    }
}
=======
public class VendingMachine {
    public static final int[] DENOMINATIONS = {1, 5, 10, 20, 50, 100};
    protected Items.ItemSlot[] itemSlots = new Items.ItemSlot[30];
    public double moneyTotal;
    public double moneyEarned;
    public int[] changeDenominations;

    public VendingMachine() {
        itemSlots = new Items.ItemSlot[10];
        for (int i = 0; i < itemSlots.length; i++) {
            itemSlots[i] = new Items.ItemSlot();
        }
        moneyTotal = 0;
        moneyEarned = 0;
        changeDenominations = new int[DENOMINATIONS.length];
    }

    public void addItem(String name, double price, int calories) {
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            if (slot.getItem() == null) {
                Items.Item item = new Items.Item(0, name, price, calories, false, Items.Item.ItemType.MILK_TEA);
                slot.setItem(item);
                break;
            }
        }
    }
}
>>>>>>> b145b2538ff02a0f9884fd9e4fdacd0282577dfd
