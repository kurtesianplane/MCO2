import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The VendingMachinePurchase class facilitates the purchase process for items from the vending machine.
 * It interacts with the user through custom JOptionPanes to choose denominations for payment and select items for purchase.
 * The class handles payment calculations, stock availability, and change dispensing.
 */
public class VendingMachinePurchase {
    private VendingMachine vendingMachine;

    /**
     * Constructs a new VendingMachinePurchase instance with the specified VendingMachine.
     *
     * @param vendingMachine The VendingMachine to be used for the purchase process.
     */
    public VendingMachinePurchase(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * Initiates the purchase process, allowing the user to select denominations for payment and items to purchase.
     *
     * @param frame The JFrame on which to display the custom JOptionPanes.
     */
    public void purchaseItem(JFrame frame) {
        StringBuilder sb = new StringBuilder();
        double payment = 0;
        List<Integer> chosenDenominations = new ArrayList<>();
        while (true) {
            Object[] options = new Object[VendingMachine.DENOMINATIONS.length + 1];
            for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
                options[i] = VendingMachine.DENOMINATIONS[i];
            }
            options[VendingMachine.DENOMINATIONS.length] = "Done";

            // Display chosen denominations and total payment
            sb.setLength(0);
            sb.append("<html><b>Chosen denominations:</b><br>");
            Map<Integer, Integer> denominationCounts = new HashMap<>();
            for (int d : chosenDenominations) {
                denominationCounts.put(d, denominationCounts.getOrDefault(d, 0) + 1);
            }
            for (Map.Entry<Integer, Integer> entry : denominationCounts.entrySet()) {
                sb.append("x").append(entry.getValue()).append(" ").append(entry.getKey()).append(" bills<br>");
            }
            sb.append("<b>Total payment: ").append(payment).append("</b></html>");

            // Customize the JOptionPane appearance
            UIManager.put("OptionPane.background", new Color(255, 255, 230));
            UIManager.put("Panel.background", new Color(255, 255, 230));
            UIManager.put("Button.background", new Color(255, 204, 204));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

            // Show the custom JOptionPane for choosing denominations
            int choice = JOptionPane.showOptionDialog(frame,
                    sb.toString(),
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
            // Prompt user to select item using buttons
            Object[] options = new Object[vendingMachine.itemSlots.length + 1];
            for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
                Items.ItemSlot slot = vendingMachine.itemSlots[i];
                Items.Item item = slot.getItem();
                if (item != null && !item.isIngredient()) {
                    options[i] = item.getName();
                } else {
                    options[i] = "Empty";
                }
            }
            options[vendingMachine.itemSlots.length] = "Cancel";

            // Show the custom JOptionPane for choosing the item
            int slotIndex = JOptionPane.showOptionDialog(frame,
                    "Select item:",
                    "Vending Machine",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            // Customize the JOptionPane appearance for the purchase dialog
            UIManager.put("OptionPane.background", new Color(255, 255, 230));
            UIManager.put("Panel.background", new Color(255, 255, 230));
            UIManager.put("Button.background", new Color(255, 204, 204));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

            if (slotIndex == vendingMachine.itemSlots.length) {
                exit = true;
            } else if (slotIndex >= 0 && slotIndex < vendingMachine.itemSlots.length) {
                Items.ItemSlot slot = vendingMachine.itemSlots[slotIndex];
                Items.Item item = slot.getItem();

                if (item != null) {
                    // Display price and calories before confirming purchase
                    sb.setLength(0);
                    sb.append("<html><b>Price: " + item.getPrice() + "<br>Calories: " + item.getCalories() + "<br>Total payment: " + payment + "<br>Do you want to proceed with purchase?</b></html>");
                    int confirmPurchase = JOptionPane.showConfirmDialog(frame,
                            sb.toString(),
                            "Confirm Purchase",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmPurchase == JOptionPane.YES_OPTION) {
                        String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
                        try {
                            int quantity = Integer.parseInt(quantityStr);
                            int quantityAvailable = item.getQuantity();
                            if (quantityAvailable > 0) {
                                if (quantity <= 0) {
                                    sb.setLength(0);
                                    sb.append("Invalid quantity. Please enter a positive number.\n");
                                    JOptionPane.showMessageDialog(frame, sb.toString());
                                } else if (quantity > quantityAvailable) {
                                    sb.setLength(0);
                                    sb.append("Insufficient stock. Only " + quantityAvailable + " available.\n");
                                    JOptionPane.showMessageDialog(frame, sb.toString());
                                } else {
                                    double totalPrice = quantity * item.getPrice();
                                    double change = payment - totalPrice;
                                    if (change >= 0) {
                                        sb.setLength(0);
                                        sb.append("<html><b>Change: " + change + "</b><br>");
                                        boolean canDispenseChange = true;
                                        for (int i = VendingMachine.DENOMINATIONS.length - 1; i >= 0; i--) {
                                            int denomination = VendingMachine.DENOMINATIONS[i];
                                            int count = (int) (change / denomination);
                                            count = Math.min(count, vendingMachine.changeDenominations[i]);
                                            if (count > 0) {
                                                sb.append(count + " x " + denomination + "<br>");
                                                change -= count * denomination;
                                                vendingMachine.changeDenominations[i] -= count;
                                            }
                                        }
                                        if (change > 0.001) {
                                            canDispenseChange = false;
                                            sb.setLength(0);
                                            sb.append("Cannot dispense exact change. Transaction cancelled.\n");
                                            JOptionPane.showMessageDialog(frame, sb.toString());
                                        } else {
                                            sb.append("</html>");
                                            JOptionPane.showMessageDialog(frame, sb.toString());
                                            sb.setLength(0);
                                            sb.append("Dispensing " + quantity + " x " + item.getName() + "\n");
                                            JOptionPane.showMessageDialog(frame, sb.toString());
                                            payment -= totalPrice;
                                            item.setQuantity(-quantity);
                                            slot.setTotalSold(quantity);
                                            vendingMachine.moneyEarned += totalPrice;
                                            vendingMachine.moneyTotal += totalPrice;
                                        }
                                    } else {
                                        sb.setLength(0);
                                        sb.append("Insufficient payment.\n");
                                        JOptionPane.showMessageDialog(frame, sb.toString());
                                    }
                                }
                            } else {
                                sb.setLength(0);
                                sb.append("Out of stock.\n");
                                JOptionPane.showMessageDialog(frame, sb.toString());
                            }
                        } catch (NumberFormatException e) {
                            sb.setLength(0);
                            sb.append("Invalid quantity. Please enter a valid number.\n");
                            JOptionPane.showMessageDialog(frame, sb.toString());
                        }
                    }
                } else {
                    sb.setLength(0);
                    sb.append("Slot " + slotIndex + " is empty.\n");
                    JOptionPane.showMessageDialog(frame, sb.toString());
                }
            }
        }
        resetCustomUIManager();
    }

    /**
     * Resets the custom UIManager changes made during the purchase process.
     * This method should be called after the purchase process is complete to revert the appearance settings.
     */
    private void resetCustomUIManager() {
        // Reset the customizations
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.font", null);
        UIManager.put("OptionPane.messageFont", null);
    }
}
