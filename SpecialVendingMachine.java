import java.util.Scanner;
import javax.swing.*;

public class SpecialVendingMachine extends VendingMachine {
    private double discount;

    public SpecialVendingMachine(double discount) {
        super();
        this.discount = discount;
    }

    public void customizeItem(JFrame frame) {
        // prompt user to select base item
        String baseItemStr = JOptionPane.showInputDialog(frame, "Enter base item slot: ");
        int baseItemSlot = Integer.parseInt(baseItemStr);
        Items.Item baseItem = itemSlots[baseItemSlot].getItem();
        double totalPrice = baseItem.getPrice();

        // prompt user to select additional ingredients
        StringBuilder sb = new StringBuilder();
        sb.append("Additional Ingredients:\n");
        for (int i = 0; i < itemSlots.length; i++) {
            Items.Item item = itemSlots[i].getItem();
            if (item != null && i != baseItemSlot) {
                sb.append(i + ": " + item.getName() + " - " + item.getPrice() + "\n");
            }
        }
        String additionalIngredientsStr = JOptionPane.showInputDialog(frame, sb.toString());
        String[] additionalIngredients = additionalIngredientsStr.split(",");
        for (String ingredient : additionalIngredients) {
            int ingredientSlot = Integer.parseInt(ingredient);
            Items.Item item = itemSlots[ingredientSlot].getItem();
            totalPrice += item.getPrice();
        }

        // display total price
        JOptionPane.showMessageDialog(frame, "Total Price: " + totalPrice);

        // display preparation steps
        JOptionPane.showMessageDialog(frame, "Preparing base item...");
        for (String ingredient : additionalIngredients) {
            int ingredientSlot = Integer.parseInt(ingredient);
            Items.Item item = itemSlots[ingredientSlot].getItem();
            JOptionPane.showMessageDialog(frame, "Adding " + item.getName() + "...");
        }
        JOptionPane.showMessageDialog(frame, "Finalizing product...");
        JOptionPane.showMessageDialog(frame, "Product is ready!");
    }

    public String purchaseItem(JFrame frame, Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        double payment = 0;
        while (true) {
            String denominationStr = JOptionPane.showInputDialog(frame, "Enter a denomination or -1 to stop: ");
            int denomination = Integer.parseInt(denominationStr);
            if (denomination == -1) {
                break;
            }
            boolean validDenomination = false;
            for (int d : DENOMINATIONS) {
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

        String slotIndexStr = JOptionPane.showInputDialog(frame, "Enter item slot: ");
        int slotIndex = Integer.parseInt(slotIndexStr);
        String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
        int quantity = Integer.parseInt(quantityStr);

        if (slotIndex >= 0 && slotIndex < itemSlots.length) {
            Items.ItemSlot slot = itemSlots[slotIndex];
            Items.Item item = slot.getItem();

            if (item != null) {
                int quantityAvailable = item.getQuantity();

                if (quantityAvailable > 0) {
                    if (quantity > quantityAvailable) {
                        sb.append("Insufficient stock. Only " + quantityAvailable + " available.\n");
                    } else {
                        double totalPrice = quantity * item.getPrice();
                        double discountedPrice = totalPrice * (1 - discount);
                        double change = payment - discountedPrice;

                        if (change >= 0) {
                            sb.append("Dispensing " + quantity + " x " + item.getName() + "\n");
                            payment -= discountedPrice;
                            item.setQuantity(-quantity);
                            slot.setTotalSold(quantity);
                            moneyEarned += discountedPrice;
                            moneyTotal += discountedPrice;

                            sb.append("Change: " + change + "\n");
                            for (int denomination : DENOMINATIONS) {
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
        return sb.toString();
    }
}
