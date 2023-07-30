import javax.swing.*;

public class VendingMachinePurchase {
    private VendingMachine vendingMachine;

    public VendingMachinePurchase(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void purchaseItem(JFrame frame) {
        StringBuilder sb = new StringBuilder();
        double payment = 0;
        while (true) {
            String denominationStr = JOptionPane.showInputDialog(frame, "Enter a denomination or -1 to stop: ");
            int denomination = Integer.parseInt(denominationStr);
            if (denomination == -1) {
                break;
            }
            boolean validDenomination = false;
            for (int d : VendingMachine.DENOMINATIONS) {
                if (denomination == d) {
                    validDenomination = true;
                    break;
                }
            }
            if (!validDenomination) {
                JOptionPane.showMessageDialog(frame, "Invalid denomination. Please try again.");
                continue;
            }
            payment += denomination;
        }
    
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
                int quantityAvailable = item.getQuantity();
    
                if (quantityAvailable > 0) {
                    if (quantity > quantityAvailable) {
                        sb.append("Insufficient stock. Only " + quantityAvailable + " available.\n");
                    } else {
                        double totalPrice = quantity * item.getPrice();
                        double change = payment - totalPrice;
    
                        if (change >= 0) {
                            sb.append("Dispensing " + quantity + " x " + item.getName() + "\n");
                            payment -= totalPrice;
                            item.setQuantity(-quantity);
                            slot.setTotalSold(quantity);
                            vendingMachine.moneyEarned += totalPrice;
                            vendingMachine.moneyTotal += totalPrice;
    
                            sb.append("Change: " + change + "\n");
                            for (int denomination : VendingMachine.DENOMINATIONS) {
                                int count = (int)(change / denomination);
                                if (count > 0) {
                                    sb.append(count + " x " + denomination + "\n");
                                    change -= count * denomination;
                                }
                            }
                        } else {
                            sb.append("Insufficient payment.\n");
                        }
                    }
                } else {
                    sb.append("Out of stock.\n");
                }
            } else {
                sb.append("Slot " + slotIndex + " is empty.\n");
            }
        } else {
            sb.append("Invalid slot index.\n");
        }
        JOptionPane.showMessageDialog(frame, sb.toString());
    }    
}
