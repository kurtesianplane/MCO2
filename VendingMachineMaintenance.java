import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The VendingMachineMaintenance class handles maintenance tasks for the vending machine.
 * It provides functionality for restocking items, setting item prices, replenishing cash denominations,
 * displaying transactions, and restocking ingredients for special vending machines.
 */
public class VendingMachineMaintenance {
    private VendingMachine vendingMachine;

    /**
     * Constructs a new VendingMachineMaintenance instance with the specified VendingMachine.
     *
     * @param vendingMachine The VendingMachine to be used for maintenance tasks.
     */
    public VendingMachineMaintenance(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    /**
     * Initiates the restocking process for items in the vending machine.
     * Allows the user to select items to restock and the quantity to restock.
     *
     * @param frame The JFrame on which to display the custom JOptionPanes.
     */
    public void restockItem(JFrame frame) {
        // Prompt user to select item using buttons
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
    
        // Customize the JOptionPane appearance
        applyCustomUIManager();
    
        int slotIndex = JOptionPane.showOptionDialog(frame,
                "Select item:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    
        resetCustomUIManager();
    
        if (slotIndex == vendingMachine.itemSlots.length) {
            // Restock all items
            // Create options array for selecting quantity
            Object[] quantityOptions = new Object[11];
            for (int i = 0; i <= 10; i++) {
                quantityOptions[i] = i;
            }
    
            // Customize the JOptionPane appearance for quantity selection
            applyCustomUIManager();
    
            int quantity = (int) JOptionPane.showInputDialog(frame,
                    "Select quantity:",
                    "Vending Machine",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    quantityOptions,
                    quantityOptions[0]);
    
            resetCustomUIManager();
    
            // Restock all items with the selected quantity
            StringBuilder sb = new StringBuilder();
            sb.append("<html>Restocked items:<br>");
            for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
                Items.ItemSlot slot = vendingMachine.itemSlots[i];
                Items.Item item = slot.getItem();
                if (item != null) {
                    int currentQuantity = item.getQuantity();
                    int totalQuantity = currentQuantity + quantity;
                    if (totalQuantity > 10) {
                        sb.append(item.getName() + ": " + (10 - currentQuantity) + "<br>");
                        item.setQuantity(10);
                    } else {
                        sb.append(item.getName() + ": " + quantity + "<br>");
                        item.setQuantity(totalQuantity);
                    }
                }
            }
            sb.append("</html>");
            JOptionPane.showMessageDialog(frame, sb.toString());
        } else if (slotIndex >= 0 && slotIndex < vendingMachine.itemSlots.length) {
            Items.ItemSlot slot = vendingMachine.itemSlots[slotIndex];
            Items.Item item = slot.getItem();
    
            if (item != null) {
                // Create options array for selecting quantity
                Object[] quantityOptions = new Object[11];
                for (int i = 0; i <= 10; i++) {
                    quantityOptions[i] = i;
                }
    
                // Customize the JOptionPane appearance for quantity selection
                applyCustomUIManager();
    
                int quantity = (int) JOptionPane.showInputDialog(frame,
                        "Select quantity:",
                        "Vending Machine",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        quantityOptions,
                        quantityOptions[0]);
    
                resetCustomUIManager();
    
                int currentQuantity = item.getQuantity();
                int totalQuantity = currentQuantity + quantity;
                if (totalQuantity > 10) {
                    JOptionPane.showMessageDialog(frame, "Cannot restock more than 10 items per slot. Please select a new quantity.");
                } else {
                    item.setQuantity(totalQuantity);
                    JOptionPane.showMessageDialog(frame, "<html>Restocked items:<br>" + item.getName() + ": " + quantity + "</html>");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Slot " + slotIndex + " is empty.");
            }
        }
    }    
    
    /**
     * Allows the user to set the price of an item in the vending machine.
     *
     * @param frame The JFrame on which to display the custom JOptionPanes.
     */
    public void setItemPrice(JFrame frame) {
        // Prompt user to select the item using buttons
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
    
        // Customize the JOptionPane appearance for item selection
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
        int slotIndex = JOptionPane.showOptionDialog(frame,
                "Select item:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    
        resetCustomUIManager();
    
        if (slotIndex >= 0 && slotIndex < vendingMachine.itemSlots.length) {
            Items.ItemSlot slot = vendingMachine.itemSlots[slotIndex];
            Items.Item item = slot.getItem();
    
            if (item != null) {
                // Create a custom input dialog for entering the new price
                JTextField priceField = new JTextField();
                priceField.setFont(new Font("Arial", Font.PLAIN, 16));
    
                Object[] message = {
                        "Enter new price:", priceField
                };
    
                // Customize the JOptionPane appearance for the input dialog
                UIManager.put("OptionPane.background", new Color(255, 255, 230));
                UIManager.put("Panel.background", new Color(255, 255, 230));
                UIManager.put("Button.background", new Color(255, 204, 204));
                UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
                int option = JOptionPane.showOptionDialog(frame, message, "Vending Machine",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    
                resetCustomUIManager();
    
                if (option == JOptionPane.OK_OPTION) {
                    String priceStr = priceField.getText();
                    try {
                        double price = Double.parseDouble(priceStr);
                        item.setPrice(price);
                        JOptionPane.showMessageDialog(frame, item.getName() + " price set to " + price);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(frame, "Invalid price. Please enter a valid number.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Slot " + slotIndex + " is empty.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid slot index.");
        }
    }    

    /**
     * Replenishes the cash denominations in the vending machine.
     * Allows the user to select denominations to replenish and the quantity to add.
     *
     * @param frame The JFrame on which to display the custom JOptionPanes.
     */
    public void replenishCash(JFrame frame) {
        List<Integer> chosenDenominations = new ArrayList<>();
        boolean exit = false; // added exit variable to track when to exit the method
        while (!exit) { // changed while loop condition to check exit variable
            // Create options array from VendingMachine.DENOMINATIONS
            Object[] options = new Object[VendingMachine.DENOMINATIONS.length + 2];
            for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
                options[i] = VendingMachine.DENOMINATIONS[i];
            }
            options[VendingMachine.DENOMINATIONS.length] = "Replenish All";
            options[VendingMachine.DENOMINATIONS.length + 1] = "Done";
    
            // Customize the JOptionPane appearance for denomination selection
            applyCustomUIManager();
    
            // Prompt user to select denomination using buttons
            int choice = JOptionPane.showOptionDialog(frame,
                    "Select a denomination:",
                    "Vending Machine",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
    
            resetCustomUIManager();
    
            if (choice == VendingMachine.DENOMINATIONS.length + 1) {
                exit = true; // added exit = true to exit the method and return to the previous menu
            } else if (choice == VendingMachine.DENOMINATIONS.length) {
                applyCustomUIManager();
                String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
                        vendingMachine.changeDenominations[i] += quantity;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a valid number.");
                }
            } else {
                int denominationIndex = choice;
                String quantityStr = JOptionPane.showInputDialog(frame, "Enter quantity: ");
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    vendingMachine.changeDenominations[denominationIndex] += quantity;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a valid number.");
                }
            }
        }
    
        // Display replenished denominations
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Replenished denominations:<br>");
        for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
            sb.append(VendingMachine.DENOMINATIONS[i] + ": " + vendingMachine.changeDenominations[i] + "<br>");
        }
        sb.append("</html>");
    
        // Customize the JOptionPane appearance for the message dialog
        applyCustomUIManager();
        JOptionPane.showMessageDialog(frame, sb.toString(), "Vending Machine", JOptionPane.INFORMATION_MESSAGE);
    
        resetCustomUIManager();
    }    

    /**
     * Displays a table of transactions showing items sold and their corresponding sales.
     * The table includes the item name, total sold, and total sales for each item.
     */
    public void displayTransactions() {
        // Create table model and set column names
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Name", "Total Sold", "Total Sales"});
    
        // Add rows to table model
        for (Items.ItemSlot slot : vendingMachine.itemSlots) {
            Items.Item item = slot.getItem();
            if (item != null) {
                int totalSold = slot.getTotalSold();
                double totalSales = totalSold * item.getPrice();
                model.addRow(new Object[]{item.getName(), totalSold, totalSales});
            }
        }
    
        // Create and customize the JTable
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
        // Set custom colors for the table
        Color headerColor = new Color(255, 200, 200);
        Color rowColor = new Color(230, 230, 255);
        table.getTableHeader().setBackground(headerColor);
        table.setBackground(rowColor);
    
        // Create scroll pane and add the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 400));
    
        // Create a panel to hold the table and add some padding
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(scrollPane, BorderLayout.CENTER);
    
        // Show the table in a custom JOptionPane
        JOptionPane.showOptionDialog(
                null,
                panel,
                "Vending Machine Transactions",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{},
                null
        );
    }    
    
    /**
     * Initiates the restocking process for ingredients in a special vending machine.
     * Allows the user to select ingredients to restock and the quantity to restock for each ingredient.
     *
     * @param frame The JFrame on which to display the custom JOptionPanes.
     */
    public void restockIngredients(JFrame frame) {
        if (vendingMachine instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) vendingMachine;
            List<Items.Item> standaloneIngredients = specialVendingMachine.getStandaloneIngredients();
            List<Items.Item> addonIngredients = specialVendingMachine.getAddonIngredients();
    
            // Create a panel to hold the buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(0, 4, 10, 10)); // 4 buttons per row, with gaps
    
            // Add "Restock All" button
            JButton restockAllButton = new JButton("Restock All");
            restockAllButton.setFont(new Font("Arial", Font.BOLD, 16));
            restockAllButton.setBackground(new Color(255, 204, 204));
            restockAllButton.addActionListener(e -> {
                // Prompt user to select quantity using buttons 1 to 10
                Object[] quantityOptions = new Object[10];
                for (int i = 0; i < 10; i++) {
                    quantityOptions[i] = i + 1;
                }
    
                UIManager.put("OptionPane.background", new Color(255, 255, 230));
                UIManager.put("Panel.background", new Color(255, 255, 230));
                UIManager.put("Button.background", new Color(255, 204, 204));
                UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
                int choice = JOptionPane.showOptionDialog(buttonPanel,
                        "Select quantity:",
                        "Vending Machine",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        quantityOptions,
                        quantityOptions[0]);
    
                resetCustomUIManager();
    
                if (choice >= 0) {
                    int quantity = (int) quantityOptions[choice];
    
                    // Restock all standalone ingredients
                    StringBuilder sb = new StringBuilder();
                    sb.append("<html>Restocked ingredients:<br>");
                    for (Items.Item item : standaloneIngredients) {
                        int currentQuantity = item.getQuantity();
                        int totalQuantity = currentQuantity + quantity;
                        if (totalQuantity > 10) {
                            sb.append(item.getName() + ": " + (10 - currentQuantity) + "<br>");
                            item.setQuantity(10);
                        } else {
                            sb.append(item.getName() + ": " + quantity + "<br>");
                            item.setQuantity(totalQuantity);
                        }
                    }
                    // Restock all addon ingredients
                    for (Items.Item item : addonIngredients) {
                        int currentQuantity = item.getQuantity();
                        int totalQuantity = currentQuantity + quantity;
                        if (totalQuantity > 10) {
                            sb.append(item.getName() + ": " + (10 - currentQuantity) + "<br>");
                            item.setQuantity(10);
                        } else {
                            sb.append(item.getName() + ": " + quantity + "<br>");
                            item.setQuantity(totalQuantity);
                        }
                    }
                    sb.append("</html>");
                    JOptionPane.showMessageDialog(buttonPanel, sb.toString());
                }
            });
            buttonPanel.add(restockAllButton);
    
            // Add buttons for standalone ingredients
            for (Items.Item item : standaloneIngredients) {
                JButton button = new JButton(item.getName());
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setBackground(new Color(255, 204, 204));
                button.addActionListener(e -> {
                    selectQuantityAndRestock(buttonPanel, item);
                });
                buttonPanel.add(button);
            }
    
            // Add buttons for addon ingredients
            for (Items.Item item : addonIngredients) {
                JButton button = new JButton(item.getName());
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setBackground(new Color(255, 204, 204));
                button.addActionListener(e -> {
                    selectQuantityAndRestock(buttonPanel, item);
                });
                buttonPanel.add(button);
            }
    
            // Create the OK button to go back to the previous menu
            JButton okButton = new JButton("OK");
            okButton.setFont(new Font("Arial", Font.BOLD, 16));
            okButton.setBackground(new Color(255, 204, 204));
            okButton.addActionListener(e -> {
                frame.dispose(); // Close the dialog when the OK button is clicked
            });
    
            // Create a panel to hold the OK button
            JPanel okButtonPanel = new JPanel();
            okButtonPanel.add(okButton);
    
            // Create a panel to hold both the button panel and the OK button panel
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(buttonPanel, BorderLayout.CENTER);
            mainPanel.add(okButtonPanel, BorderLayout.SOUTH);
    
            // Create the dialog
            JDialog dialog = new JDialog(frame, "Vending Machine", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.add(mainPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "This feature is only available for Special Vending Machines.");
        }
    }
    
    /**
     * Prompt user to select quantity and restock the given item.
     *
     * @param parent the parent component of the dialog
     * @param item   the item to restock
     */
    private void selectQuantityAndRestock(Component parent, Items.Item item) {
        // Prompt user to select quantity using buttons 1 to 10
        Object[] quantityOptions = new Object[10];
        for (int i = 0; i < 10; i++) {
            quantityOptions[i] = i + 1;
        }
    
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
        int choice = JOptionPane.showOptionDialog(parent,
                "Select quantity for " + item.getName() + ":",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                quantityOptions,
                quantityOptions[0]);
    
        resetCustomUIManager();
    
        if (choice >= 0) {
            int quantity = (int) quantityOptions[choice];
            int currentQuantity = item.getQuantity();
            int totalQuantity = currentQuantity + quantity;
            if (totalQuantity > 10) {
                JOptionPane.showMessageDialog(parent, "Cannot restock more than 10 items per slot. Please select a new quantity.");
            } else {
                item.setQuantity(totalQuantity);
                JOptionPane.showMessageDialog(parent, "<html>Restocked ingredients:<br>" + item.getName() + ": " + quantity + "</html>");
            }
        }
    }
    
    // Reset the UIManager to default values
    private void resetCustomUIManager() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.font", null);
        UIManager.put("OptionPane.messageFont", null);
    }
    // Sets the UI on the dialogs
    private void applyCustomUIManager() {
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    }
}
