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

    public double getDiscount() {
        return discount;
    }

    public void testCustomProductFeatures(JFrame frame) {
        boolean exit = false;
        while (!exit) {
            Object[] options = {"Display Products", "Purchase Custom Product", "Purchase Ingredient", "Exit"};
            int choice = JOptionPane.showOptionDialog(frame,
                "Select an option:",
                "Special Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            
            switch (choice) {
                case 0:
                    // display products
                    displayProducts(frame);
                    break;
                case 1:
                    // purchase custom product
                    purchaseCustomProduct(frame);
                    break;
                case 2:
                    // purchase ingredient
                    purchaseIngredient(frame);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Invalid choice.");
            }
        }
    }

    private void displayProducts(JFrame frame) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Milk Teas:<br>");
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && item.getItemType() == Items.Item.ItemType.MILK_TEA) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
            }
        }
        sb.append("</html>");
        JOptionPane.showMessageDialog(frame, sb.toString());
        
        sb = new StringBuilder();
        sb.append("<html>Standalone Ingredients:<br>");
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && item.getItemType() == Items.Item.ItemType.STANDALONE_INGREDIENT) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
            }
        }
        sb.append("</html>");
        JOptionPane.showMessageDialog(frame, sb.toString());
        
        sb = new StringBuilder();
        sb.append("<html>Add-On Ingredients:<br>");
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && item.getItemType() == Items.Item.ItemType.ADDON_INGREDIENT) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
            }
        }
        sb.append("</html>");
        JOptionPane.showMessageDialog(frame, sb.toString());
    }
    

    private void purchaseCustomProduct(JFrame frame) {
        // prompt user to select base milk tea
        Object[] options = new Object[itemSlots.length];
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && item.getItemType() == Items.Item.ItemType.MILK_TEA) {
                options[i] = item.getName();
            } else {
                options[i] = "Empty";
            }
        }
        int baseMilkTeaIndex = JOptionPane.showOptionDialog(frame,
            "Select base milk tea:",
            "Special Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (baseMilkTeaIndex >= 0 && baseMilkTeaIndex < itemSlots.length) {
            Items.ItemSlot slot = itemSlots[baseMilkTeaIndex];
            Items.Item baseMilkTea = slot.getItem();
            
            if (baseMilkTea != null) {
                double totalPrice = baseMilkTea.getPrice();
                
                // prompt user to select additional ingredients using checkboxes
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                List<JCheckBox> checkBoxes = new ArrayList<>();
                for (int i = 0; i < itemSlots.length; i++) {
                    Items.ItemSlot addOnSlot = itemSlots[i];
                    Items.Item addOnItem = addOnSlot.getItem();
                    if (addOnItem != null && addOnItem.getItemType() == Items.Item.ItemType.ADDON_INGREDIENT) {
                        JCheckBox checkBox = new JCheckBox(addOnItem.getName() + " - " + addOnItem.getPrice());
                        checkBoxes.add(checkBox);
                        panel.add(checkBox);
                    }
                }
                int result = JOptionPane.showConfirmDialog(frame, panel, "Select additional ingredients", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        JCheckBox checkBox = checkBoxes.get(i);
                        if (checkBox.isSelected()) {
                            Items.ItemSlot addOnSlot = itemSlots[i];
                            Items.Item addOnItem = addOnSlot.getItem();
                            totalPrice += addOnItem.getPrice();
                        }
                    }
                }
                
                // apply discount
                totalPrice *= (1 - getDiscount());
                
                // display total price
                JOptionPane.showMessageDialog(frame, "Total Price: " + totalPrice);
                
                // display preparation steps
                JOptionPane.showMessageDialog(frame, "Preparing base milk tea...");
                for (int i = 0; i < checkBoxes.size(); i++) {
                    JCheckBox checkBox = checkBoxes.get(i);
                    if (checkBox.isSelected()) {
                        Items.ItemSlot addOnSlot = itemSlots[i];
                        Items.Item addOnItem = addOnSlot.getItem();
                        JOptionPane.showMessageDialog(frame, "Adding " + addOnItem.getName() + "...");
                    }
                }
                JOptionPane.showMessageDialog(frame, "Finalizing product...");
                JOptionPane.showMessageDialog(frame, "Product is ready!");
                
                // update total sold
                slot.setTotalSold(1);
                
                // update money earned and total money
                moneyEarned += totalPrice;
                moneyTotal += totalPrice;
            } else {
                JOptionPane.showMessageDialog(frame, "Slot is empty.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid slot index.");
        }
    }

    private void purchaseIngredient(JFrame frame) {
        Object[] options = new Object[itemSlots.length];
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && item.getItemType() == Items.Item.ItemType.STANDALONE_INGREDIENT) {
                options[i] = item.getName();
            } else {
                options[i] = "Empty";
            }
        }
        int slotIndex = JOptionPane.showOptionDialog(frame,
            "Select standalone ingredient:",
            "Special Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (slotIndex >= 0 && slotIndex < itemSlots.length) {
            Items.ItemSlot slot = itemSlots[slotIndex];
            Items.Item item = slot.getItem();
            
            if (item != null) {
                String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
                int quantity = Integer.parseInt(quantityStr);
                
                double totalPrice = quantity * item.getPrice();
                
                // display total price
                JOptionPane.showMessageDialog(frame, "Total Price: " + totalPrice);
                
                // display preparation steps
                JOptionPane.showMessageDialog(frame, "Preparing standalone ingredient...");
                JOptionPane.showMessageDialog(frame, "Dispensing " + quantity + " x " + item.getName());
                JOptionPane.showMessageDialog(frame, "Product is ready!");
                
                // update quantity and total sold
                item.setQuantity(-quantity);
                slot.setTotalSold(quantity);
                
                // update money earned and total money
                moneyEarned += totalPrice;
                moneyTotal += totalPrice;
            } else {
                JOptionPane.showMessageDialog(frame, "Slot is empty.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid slot index.");
        }
    }

    public void addItem(String name, double price, int calories) {
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            if (slot.getItem() == null) {
                Items.Item item = new Items.Item(0, name, price, calories, false, Items.Item.ItemType.MILK_TEA);
                slot.setItem(item);
                break;
            }
        }
    }

    public void addStandaloneItem(String name, double price, int calories) {
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            if (slot.getItem() == null) {
                Items.Item item = new Items.Item(0, name, price, calories, false, Items.Item.ItemType.STANDALONE_INGREDIENT);
                slot.setItem(item);
                break;
            }
        }
    }

    public void addAddonItem(String name, double price, int calories) {
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            if (slot.getItem() == null) {
                Items.Item item = new Items.Item(0, name, price, calories, false, Items.Item.ItemType.ADDON_INGREDIENT);
                slot.setItem(item);
                break;
            }
        }
    }
}


