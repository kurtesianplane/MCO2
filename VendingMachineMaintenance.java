import java.util.ArrayList;
import java.util.List;

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
    
        Items.ItemSlot slot = vendingMachine.itemSlots[slotIndex];
        Items.Item item = slot.getItem();
    
        if (item != null) {
            // create options array for selecting quantity
            Object[] quantityOptions = new Object[11];
            for (int i = 0; i <= 10; i++) {
                quantityOptions[i] = i;
            }
    
            // prompt user to select quantity using buttons
            int quantity = JOptionPane.showOptionDialog(frame,
                "Select quantity:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                quantityOptions,
                quantityOptions[0]);
    
            // check if selected quantity would cause total quantity to exceed 10
            if (item.getQuantity() + quantity > 10) {
                // display message and ask user to select new quantity
                JOptionPane.showMessageDialog(frame, "Cannot restock more than 10 items per slot. Please select a new quantity.");
            } else {
                // restock item with selected quantity
                item.setQuantity(quantity);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Slot " + slotIndex + " is empty.");
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
        try {
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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid price. Please enter a valid number.");
        }
    }
    
    public void replenishCash(JFrame frame) {
        List<Integer> chosenDenominations = new ArrayList<>();
        while (true) {
            // create options array from VendingMachine.DENOMINATIONS
            Object[] options = new Object[VendingMachine.DENOMINATIONS.length + 2];
            for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
                options[i] = VendingMachine.DENOMINATIONS[i];
            }
            options[VendingMachine.DENOMINATIONS.length] = "Replenish All";
            options[VendingMachine.DENOMINATIONS.length + 1] = "Done";
            
            // prompt user to select denomination using buttons
            int choice = JOptionPane.showOptionDialog(frame,
                "Select a denomination:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            
            if (choice == VendingMachine.DENOMINATIONS.length + 1) {
                break;
            } else if (choice == VendingMachine.DENOMINATIONS.length) {
                String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
                int quantity = Integer.parseInt(quantityStr);
                
                for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
                    vendingMachine.changeDenominations[i] += quantity;
                }
            } else {
                int denominationIndex = choice;
                String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
                int quantity = Integer.parseInt(quantityStr);
                
                vendingMachine.changeDenominations[denominationIndex] += quantity;
            }
        }
        
        // display replenished denominations
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Replenished denominations:<br>");
        for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
            sb.append(VendingMachine.DENOMINATIONS[i] + ": " + vendingMachine.changeDenominations[i] + "<br>");
        }
        sb.append("</html>");
        JOptionPane.showMessageDialog(frame, sb.toString());
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
