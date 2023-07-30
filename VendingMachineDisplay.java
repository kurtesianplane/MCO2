import javax.swing.*;

public class VendingMachineDisplay {
    private VendingMachine vendingMachine;

    public VendingMachineDisplay(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void displayItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Vending Machine Items:<br>");
        for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + "<br>");
            } else {
                sb.append("Slot " + i + ": Empty<br>");
            }
        }
        sb.append("</html>");
        JOptionPane.showMessageDialog(null, sb.toString());
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
