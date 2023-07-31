import javax.swing.*;

    public class VendingMachineDisplay {
    private VendingMachine vendingMachine;
    private int currentPage = 0;
    private int itemsPerPage = 10;

    public VendingMachineDisplay(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void displayItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Vending Machine Items:<br>");
        for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && !item.isIngredient()) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
            }
        }
        sb.append("</html>");
        JOptionPane.showMessageDialog(null, sb.toString());
    }
    
    private void updateLabel(JLabel label) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Vending Machine Ingredients:<br>");
        int start = 0;
        int end = vendingMachine.itemSlots.length;
        for (int i = start; i < end; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && item.isIngredient()) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
            }
        }
        sb.append("<br>ADD-ON ONLY:<br>");
        for (int i = start; i < end; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && !item.isIngredient()) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
            }
        }
        sb.append("</html>");
        label.setText(sb.toString());
    }
    
    public void displayTransactions() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Vending Machine Transactions:<br>");
        for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null) {
                int totalSold = slot.getTotalSold();
                double totalSales = totalSold * item.getPrice();
                sb.append(item.getName() + ": " + totalSold + " sold, " + totalSales + " earned<br>");
            }
        }
        sb.append("Total earned: " + vendingMachine.moneyEarned);
        sb.append("</html>");
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
