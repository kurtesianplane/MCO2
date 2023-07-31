import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SpecialVendingMachine extends VendingMachine {
    private double discount;

    public SpecialVendingMachine(double discount) {
        super();
        this.discount = discount;
        SpecialVendingProducts.addDefaultProducts(this);
    }

    public void customizeItem(JFrame frame) {
        Object[] options = {"Individual Item", "Milk Tea", "Exit"};
        int choice = JOptionPane.showOptionDialog(frame,
            "Select an option:",
            "Special Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
    
        if (choice == 0) {
            // purchase individual item
        } else if (choice == 1) {
            // purchase milk tea
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
    
            // apply discount
            totalPrice *= (1 - discount);
    
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
        } else {
          // exit
        }
    }
    
    public void addItem(String name, double price, int calories, boolean isIngredient) {
        System.out.println("Adding item: " + name);
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            if (slot.getItem() == null) {
                Items.Item item = new Items.Item(0, name, price, calories, isIngredient);
                slot.setItem(item);
                System.out.println("Added item to slot: " + i);
                break;
            }
        }
    }
}
