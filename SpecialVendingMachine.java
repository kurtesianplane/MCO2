import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class SpecialVendingMachine extends VendingMachine {
    public List<Items.Item> standaloneIngredients;
    public List<Items.Item> addonIngredients;
    public List<Items.Item> chosenIngredients;
    public Items.Item chosenBase;

    public SpecialVendingMachine(double discount) {
        super();
        standaloneIngredients = new ArrayList<>();
        addonIngredients = new ArrayList<>();
        chosenIngredients = new ArrayList<>();
        chosenBase = null;
    }

    public void addStandaloneItem(String name, double price, int calories, Items.Item.ItemType itemType) {
        standaloneIngredients.add(new Items.Item(0, name, price, calories, true, itemType));
    }

    public void addAddonItem(String name, double price, int calories, Items.Item.ItemType itemType) {
        addonIngredients.add(new Items.Item(0, name, price, calories, true, itemType));
    }

    public List<Items.Item> getStandaloneIngredients() {
        return standaloneIngredients;
    }

    public List<Items.Item> getAddonIngredients() {
        return addonIngredients;
    }

    public void chooseBase(Items.Item base) {
        this.chosenBase = base;
    }

    public void chooseIngredient(Items.Item ingredient) {
        chosenIngredients.add(ingredient);
    }

    public int calculateCalories() {
        int totalCalories = 0;
        if (chosenBase != null) {
            totalCalories += chosenBase.getCalories();
        }
        for (Items.Item ingredient : chosenIngredients) {
            totalCalories += ingredient.getCalories();
        }
        return totalCalories;
    }

    public String displayPreparationSteps() {
        StringBuilder sb = new StringBuilder();
        sb.append("Preparation Steps:\n");
        if (chosenBase != null) {
            sb.append("- Add base: " + chosenBase.getName() + "\n");
        }
        for (Items.Item ingredient : chosenIngredients) {
            sb.append("- Add ingredient: " + ingredient.getName() + "\n");
        }
        sb.append("- Mix ingredients\n");
        sb.append("- Custom product is ready\n");
        return sb.toString();
    }

    public void purchaseItem(JFrame frame) {
        // prompt user to select item using buttons
        Object[] options = new Object[standaloneIngredients.size()];
        for (int i = 0; i < standaloneIngredients.size(); i++) {
            Items.Item item = standaloneIngredients.get(i);
            options[i] = item.getName();
        }
        int itemIndex = JOptionPane.showOptionDialog(frame,
            "Select item:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (itemIndex >= 0 && itemIndex < standaloneIngredients.size()) {
            Items.Item item = standaloneIngredients.get(itemIndex);

            // display price and calories before confirming purchase
            int confirmPurchase = JOptionPane.showConfirmDialog(frame,
                "<html>Price: " + item.getPrice() + "<br>Calories: " + item.getCalories() + "<br>Do you want to proceed with purchase?</html>",
                "Confirm Purchase",
                JOptionPane.YES_NO_OPTION);
                if (confirmPurchase == JOptionPane.YES_OPTION) {
                    double change = handlePayment(frame, item.getPrice());
                    if (change >= 0) {
                        // TODO: dispense item
                    }
                }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid item index.");
        }
    }

    public void dispenseItem(JFrame frame, Items.Item item) {
        item.setQuantity(item.getQuantity() - 1);
        JOptionPane.showMessageDialog(frame, "Dispensing item: " + item.getName());
    }    

    public double handlePayment(JFrame frame, double price) {
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

        double change = payment - price;
        if (change >= 0) {
            sb.setLength(0);
            sb.append("Change: " + change + "\n");
            boolean canDispenseChange = true;
            for (int i = VendingMachine.DENOMINATIONS.length - 1; i >= 0; i--) {
                int denomination = VendingMachine.DENOMINATIONS[i];
                int count = (int)(change / denomination);
                count = Math.min(count, changeDenominations[i]);
                if (count > 0) {
                    sb.append(count + " x " + denomination + "\n");
                    change -= count * denomination;
                    changeDenominations[i] -= count;
                }
            }
            if (change > 0.001) {
                canDispenseChange = false;
                sb.append("Cannot dispense exact change. Transaction cancelled.\n");
                JOptionPane.showMessageDialog(frame, sb.toString());
                return -1;
            } else {
                JOptionPane.showMessageDialog(frame, sb.toString());
                return change;
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Insufficient payment.\n");
            return -1;
        }
    }
}

