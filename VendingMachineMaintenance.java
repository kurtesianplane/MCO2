import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class VendingMachineMaintenance {
    private VendingMachine vendingMachine;

    public VendingMachineMaintenance(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void restockItem(JFrame frame) {
        Object[] options = new Object[vendingMachine.itemSlots.length + 1];
        for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null) {
                options[i] = item.getName();
            } else {
                options[i] = "Empty";
            }
        }
        options[vendingMachine.itemSlots.length] = "Restock All";
        int slotIndex = JOptionPane.showOptionDialog(frame,
            "Select item:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (slotIndex == vendingMachine.itemSlots.length) {
            String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
            int quantity = Integer.parseInt(quantityStr);
            
            for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
                Items.ItemSlot slot = vendingMachine.itemSlots[i];
                Items.Item item = slot.getItem();
                if (item != null) {
                    item.setQuantity(quantity);
                }
            }
        } else if (slotIndex >= 0 && slotIndex < vendingMachine.itemSlots.length) {
            Items.ItemSlot slot = vendingMachine.itemSlots[slotIndex];
            Items.Item item = slot.getItem();
            
            if (item != null) {
                String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
                int quantity = Integer.parseInt(quantityStr);
                
                item.setQuantity(quantity);
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
}
