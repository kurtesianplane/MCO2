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
                Items.Item item = new Items.Item(0, name, price, calories, false);
                slot.setItem(item);
                break;
            }
        }
    }
}
