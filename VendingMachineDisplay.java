import javax.swing.*;

    public class VendingMachineDisplay {
    private VendingMachine vendingMachine;
    private int currentPage = 0;
    private int itemsPerPage = 10;

    public VendingMachineDisplay(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void displayItems() {
        Object[] options;
        if (vendingMachine instanceof SpecialVendingMachine) {
            options = new Object[]{"Show All Products", "Show Ingredients"};
        } else {
            options = new Object[]{"Show All Products"};
        }
        int choice = JOptionPane.showOptionDialog(null,
            "Select an option:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (choice == 0) {
            // show all products
            StringBuilder sb = new StringBuilder();
            sb.append("<html>Vending Machine Items:<br>");
            for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
                Items.ItemSlot slot = vendingMachine.itemSlots[i];
                Items.Item item = slot.getItem();
                if (item != null && !item.isIngredient()) {
                    sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
                }
            }
            sb.append("</html>");
            JOptionPane.showMessageDialog(null, sb.toString());
        } else if (choice == 1 && vendingMachine instanceof SpecialVendingMachine) {
            // show ingredients
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel label = new JLabel();
            panel.add(label);
            JButton prevButton = new JButton("Previous");
            prevButton.addActionListener(e -> {
                currentPage--;
                updateLabel(label);
                prevButton.setEnabled(currentPage > 0);
            });
            prevButton.setEnabled(currentPage > 0);
            panel.add(prevButton);
            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(e -> {
                currentPage++;
                updateLabel(label);
                nextButton.setEnabled((currentPage + 1) * itemsPerPage < vendingMachine.itemSlots.length);
            });
            nextButton.setEnabled((currentPage + 1) * itemsPerPage < vendingMachine.itemSlots.length);
            panel.add(nextButton);
            updateLabel(label);
            JOptionPane.showMessageDialog(null, panel);
        }
    }

    private void updateLabel(JLabel label) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Vending Machine Ingredients:<br>");
        int start = currentPage * itemsPerPage;
        int end = Math.min(start + itemsPerPage, vendingMachine.itemSlots.length);
        for (int i = start; i < end; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && item.isIngredient()) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
            }
        }
        sb.append("<br>ADD-ON ONLY:<br>");
        for (int i = start; i < end; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && !item.isIngredient()) {
                sb.append("Slot " + i + ": " + item.getName() + " - " + item.getPrice() + " (" + item.getCalories() + " calories)<br>");
            }
        }
        sb.append("</html>");
        label.setText(sb.toString());
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
