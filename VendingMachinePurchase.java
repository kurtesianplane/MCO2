import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachinePurchase {
    private VendingMachine vendingMachine;

    public VendingMachinePurchase(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void purchaseItem(JFrame frame) {
        StringBuilder sb = new StringBuilder();
        double payment = 0;
        List<Integer> chosenDenominations = new ArrayList<>();
        while (true) {
            // create options array from VendingMachine.DENOMINATIONS
            Object[] options = new Object[VendingMachine.DENOMINATIONS.length + 1];
            for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
                options[i] = VendingMachine.DENOMINATIONS[i];
            }
            options[VendingMachine.DENOMINATIONS.length] = "Done";
            
            // display chosen denominations
            sb.setLength(0);
            sb.append("<html>Chosen denominations:<br>");
            Map<Integer, Integer> denominationCounts = new HashMap<>();
            for (int d : chosenDenominations) {
                denominationCounts.put(d, denominationCounts.getOrDefault(d, 0) + 1);
            }
            for (Map.Entry<Integer, Integer> entry : denominationCounts.entrySet()) {
                sb.append("x" + entry.getValue() + " " + entry.getKey() + " bills<br>");
            }
            sb.append("Total payment: " + payment);
            sb.append("</html>");
            JOptionPane.showMessageDialog(frame, sb.toString());
            
            // prompt user to select denomination using buttons
            int choice = JOptionPane.showOptionDialog(frame,
                "Select a denomination:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            
            if (choice == VendingMachine.DENOMINATIONS.length) {
                break;
            }
            
            int denomination = VendingMachine.DENOMINATIONS[choice];
            payment += denomination;
            chosenDenominations.add(denomination);
        }
    
        boolean exit = false;
        while (!exit) {
            // prompt user to select item using buttons
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
            options[vendingMachine.itemSlots.length] = "Cancel";
            int slotIndex = JOptionPane.showOptionDialog(frame,
                "Select item:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
      
            if (slotIndex == vendingMachine.itemSlots.length) {
                exit = true;
            } else if (slotIndex >= 0 && slotIndex < vendingMachine.itemSlots.length) {
                Items.ItemSlot slot = vendingMachine.itemSlots[slotIndex];
                Items.Item item = slot.getItem();
      
                if (item != null) {
                    // display price and calories before confirming purchase
                    int confirmPurchase = JOptionPane.showConfirmDialog(frame, 
                        "<html>Price: " + item.getPrice() + "<br>Calories: " + item.getCalories() + "<br>Total payment: " + payment + "<br>Do you want to proceed with purchase?</html>", 
                        "Confirm Purchase", 
                        JOptionPane.YES_NO_OPTION);
                    if (confirmPurchase == JOptionPane.YES_OPTION) {
                        String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
                        int quantity = Integer.parseInt(quantityStr);
      
                        int quantityAvailable = item.getQuantity();
      
                        if (quantityAvailable > 0) {
                            if (quantity > quantityAvailable) {
                                sb.append("Insufficient stock. Only " + quantityAvailable + " available.\n");
                            } else {
                                double totalPrice = quantity * item.getPrice();
                                double change = payment - totalPrice;
      
                                if (change >= 0) {
                                    sb.append("Change: " + change + "\n");
                                    boolean canDispenseChange = true;
                                    for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
                                        int denomination = VendingMachine.DENOMINATIONS[i];
                                        int count = (int)(change / denomination);
                                        count = Math.min(count, vendingMachine.changeDenominations[i]);
                                        if (count > 0) {
                                            sb.append(count + " x " + denomination + "\n");
                                            change -= count * denomination;
                                            vendingMachine.changeDenominations[i] -= count;
                                        }
                                    }
                                    if (change > 0.001) {
                                        canDispenseChange = false;
                                        sb.append("Cannot dispense exact change. Transaction cancelled.\n");
                                    }
                                    if (canDispenseChange) {
                                        sb.append("Dispensing " + quantity + " x " + item.getName() + "\n");
                                        payment -= totalPrice;
                                        item.setQuantity(-quantity);
                                        slot.setTotalSold(quantity);
                                        vendingMachine.moneyEarned += totalPrice;
                                        vendingMachine.moneyTotal += totalPrice;
                                    }
                                } else {
                                    sb.append("Insufficient payment.\n");
                                }
                            }
                        } else {
                            sb.append("Out of stock.\n");
                        }
                    }
                    JOptionPane.showMessageDialog(frame, sb.toString());
                } else {
                    sb.append("Slot " + slotIndex + " is empty.\n");
                    JOptionPane.showMessageDialog(frame, sb.toString());
                }
            } else {
                sb.append("Invalid slot index.\n");
                JOptionPane.showMessageDialog(frame, sb.toString());
            }
        }
    }
}
