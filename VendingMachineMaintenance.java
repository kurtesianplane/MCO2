import javax.swing.*;

public class VendingMachineMaintenance {
    private VendingMachine vendingMachine;

    public VendingMachineMaintenance(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void restockItem(JFrame frame) {
        // prompt user to select item using buttons
        Object[] options = new Object[vendingMachine.itemSlots.length];
        for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null) {
                options[i] = item.getName();
            } else {
                options[i] = "Empty";
            }
        }
        int slotIndex = JOptionPane.showOptionDialog(frame,
            "Select item:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
    
        String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
        int quantity = Integer.parseInt(quantityStr);
    
        if (slotIndex >= 0 && slotIndex < vendingMachine.itemSlots.length) {
            Items.ItemSlot slot = vendingMachine.itemSlots[slotIndex];
            Items.Item item = slot.getItem();
    
            if (item != null) {
                item.setQuantity(quantity);
                JOptionPane.showMessageDialog(frame, item.getName() + " restocked to " + quantity);
            } else {
                JOptionPane.showMessageDialog(frame, "Slot " + slotIndex + " is empty.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid slot index.");
        }
    }    

    public void setItemPrice(JFrame frame) {
        // prompt user to select item using buttons
        Object[] options = new Object[vendingMachine.itemSlots.length];
        for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null) {
                options[i] = item.getName();
            } else {
                options[i] = "Empty";
            }
        }
        int slotIndex = JOptionPane.showOptionDialog(frame,
            "Select item:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
    
        String priceStr = JOptionPane.showInputDialog(frame, "Enter new price: ");
        double price = Double.parseDouble(priceStr);
    
        if (slotIndex >= 0 && slotIndex < vendingMachine.itemSlots.length) {
            Items.ItemSlot slot = vendingMachine.itemSlots[slotIndex];
            Items.Item item = slot.getItem();
    
            if (item != null) {
                item.setPrice(price);
                JOptionPane.showMessageDialog(frame, item.getName() + " price set to " + price);
            } else {
                JOptionPane.showMessageDialog(frame, "Slot " + slotIndex + " is empty.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid slot index.");
        }
    }
    
    public void replenishCash(JFrame frame) {
        String amountStr = JOptionPane.showInputDialog(frame, "Enter amount: ");
        double amount = Double.parseDouble(amountStr);
        vendingMachine.moneyTotal += amount;
        JOptionPane.showMessageDialog(frame, "Cash replenished to " + vendingMachine.moneyTotal);
    }    
}
