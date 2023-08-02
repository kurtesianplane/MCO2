import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * The SpecialVendingMachine class is a subclass of VendingMachine that allows the user to create
 * a special vending machine with a base item and a list of standalone and addon ingredients.
 * The user can then choose the ingredients to add to the base item to create a customized item.
 */
public class SpecialVendingMachine extends VendingMachine {
    public List<Items.Item> standaloneIngredients;
    public List<Items.Item> addonIngredients;
    public List<Items.Item> chosenIngredients;
    public Items.Item chosenBase;
    private View view;
    private Map<String, ImageIcon> imageMap;

    /**
     * Constructs a new SpecialVendingMachine object with the given discount.
     *
     * @param discount The difference of value that would apply to the items in the vending machine.
     */
    public SpecialVendingMachine(double discount) {
        super();
        standaloneIngredients = new ArrayList<>();
        addonIngredients = new ArrayList<>();
        chosenIngredients = new ArrayList<>();
        chosenBase = null;
        this.imageMap = new HashMap<>();
    }

    /**
     * Loads the images for the items in the vending machine.
     */
    public void addStandaloneItem(String name, double price, int calories, Items.Item.ItemType itemType, String imageFilename) {
        standaloneIngredients.add(new Items.Item(0, name, price, calories, true, itemType, null));
    }

        /**
     * Adds an addon item to the special vending machine.
     *
     * @param name          The name of the addon item.
     * @param price         The price of the addon item.
     * @param calories      The number of calories in the addon item.
     * @param itemType      The type of the addon item (e.g., DRINK, TOPPING, SAUCE, etc.).
     * @param imageFilename The filename of the image representing the addon item.
     */
    public void addAddonItem(String name, double price, int calories, Items.Item.ItemType itemType, String imageFilename) {
        addonIngredients.add(new Items.Item(0, name, price, calories, true, itemType, null));
    }

    /**
     * Returns a list of standalone ingredients available in the special vending machine.
     *
     * @return The list of standalone ingredients.
     */
    public List<Items.Item> getStandaloneIngredients() {
        return standaloneIngredients;
    }

    /**
     * Returns a list of addon ingredients available in the special vending machine.
     *
     * @return The list of addon ingredients.
     */
    public List<Items.Item> getAddonIngredients() {
        return addonIngredients;
    }

    /**
     * Sets the chosen base item for the special vending machine.
     *
     * @param base The base item chosen by the user.
     */
    public void chooseBase(Items.Item base) {
        this.chosenBase = base;
    }

    /**
     * Adds an ingredient item to the list of chosen ingredients for the special vending machine.
     *
     * @param ingredient The ingredient item chosen by the user.
     */
    public void chooseIngredient(Items.Item ingredient) {
        chosenIngredients.add(ingredient);
    }

    /**
     * Calculates and returns the total calories of the special vending machine's meal,
     * considering the chosen base item and any selected ingredients.
     *
     * @return The total calories of the meal.
     */
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

    /**
     * Calculates and returns the total price of the special vending machine's meal,
     * considering the chosen base item and any selected ingredients.
     *
     * @return The total price of the meal.
     */
    public void displayPreparationSteps() {
        StringBuilder sb = new StringBuilder();
        sb.append("Preparation Steps:\n");
        if (chosenBase != null) {
            sb.append("- Adding the base: ").append(chosenBase.getName()).append("\n");
        }
    
        // Create a list to keep track of unique ingredients to avoid redundancy
        List<String> uniqueIngredients = new ArrayList<>();
    
        for (Items.Item ingredient : chosenIngredients) {
            String ingredientName = ingredient.getName();
            if (!uniqueIngredients.contains(ingredientName)) {
                uniqueIngredients.add(ingredientName);
                sb.append("- ").append(ingredientName).append("\n");
            }
        }
        sb.append("- Mixing all the ingredients\n");
        sb.append("- Here is your custom product! Enjoy your ");
    
        // Append the custom product name (base and unique ingredients)
        if (chosenBase != null) {
            sb.append(chosenBase.getName()).append(" ");
        }
    
        for (int i = 0; i < uniqueIngredients.size(); i++) {
            sb.append(uniqueIngredients.get(i));
            if (i < uniqueIngredients.size() - 1) {
                sb.append(" and ");
            }
        }
        sb.append("!\n");
    
        // Customize the JOptionPane appearance
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
        // Show the preparation steps in a custom-styled JOptionPane
        JOptionPane.showMessageDialog(null, sb.toString(), "Preparation Steps", JOptionPane.INFORMATION_MESSAGE);
    
        // Reset the customizations for the next dialog
        resetCustomUIManager();
    }    
    
    /**
     * Displays the total price of the special vending machine's meal,
     * considering the chosen base item and any selected ingredients.
     */
    public void purchaseItem(JFrame frame) {
        // Create options array from standaloneIngredients
        Object[] options = new Object[standaloneIngredients.size()];
        for (int i = 0; i < standaloneIngredients.size(); i++) {
            Items.Item item = standaloneIngredients.get(i);
            options[i] = item.getName();
        }
    
        // Customize the JOptionPane appearance
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
        int itemIndex = JOptionPane.showOptionDialog(frame,
                "Select item:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    
        // Reset the customizations for the next dialog
        resetCustomUIManager();
    
        if (itemIndex >= 0 && itemIndex < standaloneIngredients.size()) {
            Items.Item item = standaloneIngredients.get(itemIndex);
    
            // Display price and calories before confirming purchase
            int confirmPurchase = JOptionPane.showConfirmDialog(frame,
                    "<html>Price: " + item.getPrice() + "<br>Calories: " + item.getCalories() + "<br>Do you want to proceed with purchase?</html>",
                    "Confirm Purchase",
                    JOptionPane.YES_NO_OPTION);
    
            // Customize the JOptionPane appearance
            UIManager.put("OptionPane.background", new Color(255, 255, 230));
            UIManager.put("Panel.background", new Color(255, 255, 230));
            UIManager.put("Button.background", new Color(255, 204, 204));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
            if (confirmPurchase == JOptionPane.YES_OPTION) {
                double change = handlePayment(frame, item.getPrice());
                if (change >= 0) {
                    dispenseItem(frame, item);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid item index.");
        }
    }    

    /**
     * Displays the total price of the special vending machine's meal,
     * considering the chosen base item and any selected ingredients.
     */
    public void dispenseItem(JFrame frame, Items.Item item) {
        item.setQuantity(item.getQuantity() - 1);
    
        // Customize the JOptionPane appearance
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
        JOptionPane.showMessageDialog(frame, "Dispensing item: " + item.getName());
    
        // Reset the customizations for the next dialog
        resetCustomUIManager();
    }    

    /**
     * Displays the total price of the special vending machine's meal,
     * considering the chosen base item and any selected ingredients.
     */
    public double handlePayment(JFrame frame, double price) {
        StringBuilder sb = new StringBuilder();
        double payment = 0;
        List<Integer> chosenDenominations = new ArrayList<>();
        while (true) {
            // Create options array from VendingMachine.DENOMINATIONS
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
                sb.append("x" + entry.getValue() + " " + entry.getKey() + " bills<br>");
            }
            sb.append("<b>Total payment: " + payment + "</b></html>");
    
            // Customize the JOptionPane appearance
            UIManager.put("OptionPane.background", new Color(255, 255, 230));
            UIManager.put("Panel.background", new Color(255, 255, 230));
            UIManager.put("Button.background", new Color(255, 204, 204));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
            int choice = JOptionPane.showOptionDialog(frame,
                    sb.toString(),
                    "Vending Machine",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
    
            // Reset the customizations for the next dialog
            resetCustomUIManager();
    
            if (choice == VendingMachine.DENOMINATIONS.length) {
                break;
            }
    
            int denomination = VendingMachine.DENOMINATIONS[choice];
            payment += denomination;
            chosenDenominations.add(denomination);
        }
    
        double change = payment - price;
        if (change >= 0.001) {
            sb.setLength(0);
            sb.append("Change: " + change + "\n");
            boolean canDispenseChange = true;
            int[] changeDenominationsCopy = Arrays.copyOf(changeDenominations, changeDenominations.length);
            for (int i = VendingMachine.DENOMINATIONS.length - 1; i >= 0; i--) {
                int denomination = VendingMachine.DENOMINATIONS[i];
                int count = (int) (change / denomination);
                count = Math.min(count, changeDenominationsCopy[i]);
                if (count > 0) {
                    sb.append(count + " x " + denomination + "\n");
                    change -= count * denomination;
                    changeDenominationsCopy[i] -= count;
                }
            }
            if (change > 0.001) {
                canDispenseChange = false;

                // Customize the JOptionPane appearance for displaying the change message
                UIManager.put("OptionPane.background", new Color(255, 255, 230));
                UIManager.put("Panel.background", new Color(255, 255, 230));
                UIManager.put("Button.background", new Color(255, 204, 204));
                UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

                JOptionPane.showMessageDialog(frame, "Cannot dispense exact change. Transaction cancelled.");
                return -1;
            } else {
                // If change can be dispensed, update the actual changeDenominations array
                for (int i = 0; i < VendingMachine.DENOMINATIONS.length; i++) {
                    changeDenominations[i] = changeDenominationsCopy[i];
                }

                // Customize the JOptionPane appearance for displaying the change amount
                UIManager.put("OptionPane.background", new Color(255, 255, 230));
                UIManager.put("Panel.background", new Color(255, 255, 230));
                UIManager.put("Button.background", new Color(255, 204, 204));
                UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

                JOptionPane.showMessageDialog(frame, "Change: " + (payment - price));
                return payment - price;
            }
        } else {
            // Customize the JOptionPane appearance for displaying the insufficient payment message
            UIManager.put("OptionPane.background", new Color(255, 255, 230));
            UIManager.put("Panel.background", new Color(255, 255, 230));
            UIManager.put("Button.background", new Color(255, 204, 204));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

            JOptionPane.showMessageDialog(frame, "Insufficient payment.\n");
            return -1;
        }
    }

    /**
     * Displays an error message for invalid choices in the special vending machine.
     */
    public void displayInvalidChoice() {
        // Display an error message for invalid choices
        JOptionPane.showMessageDialog(null, "Invalid choice!");
    }

    /**
     * Resets the custom UI manager to default settings after displaying custom dialogs.
     */
    public void resetCustomUIManager() {
        // Reset the customizations
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.font", null);
        UIManager.put("OptionPane.messageFont", null);
    }

    /**
     * Displays a custom dialog showing additional toppings and addon ingredients available in the special vending machine.
     * Allows the user to choose from the available options.
     */
    public void displayCustomProduct() {
        List<Items.Item> additionalToppings = new ArrayList<>();
        additionalToppings.addAll(getStandaloneIngredients());
        additionalToppings.addAll(getAddonIngredients());
        loadImages();

        // Create table model and set column names
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? ImageIcon.class : Object.class;
            }
        };
        tableModel.setColumnIdentifiers(new String[]{"Photos", "Stocks", "Name", "Price", "Calories"});

        // Add rows to table model for additional toppings
        for (Items.Item item : additionalToppings) {
            ImageIcon icon = imageMap.get(item.getName());
            tableModel.addRow(new Object[]{icon, item.getQuantity(), item.getName(), item.getPrice(), item.getCalories()});
        }

        // Create and customize the JTable
        JTable table = new JTable(tableModel);
        table.setRowHeight(100); // Set row height to display the images properly
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Center align the content in all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Custom renderer for the photos column
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // Set alternating row colors for better readability
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(240, 240, 240));
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });

        // Create scroll pane and add the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400)); // Adjust the preferred size as needed

        // Create a panel to hold the table and add some padding
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Customize the JOptionPane appearance
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

        // Show the table in a custom JOptionPane
        JOptionPane.showOptionDialog(
                null,
                panel,
                "Custom Product Ingredients",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{},
                null
        );

        // Reset the customizations for the next dialog
        resetCustomUIManager();
    }

    /**
     * Loads images for ingredients and stores them in the imageMap.
     * The image filenames are generated based on the item names.
     */
    private void loadImages() {
        List<Items.Item> ingredientsToLoad = new ArrayList<>();
        ingredientsToLoad.addAll(standaloneIngredients);
        ingredientsToLoad.addAll(addonIngredients);
    
        for (Items.Item item : ingredientsToLoad) {
            String filename = "/ingredients/" + getItemImageFilename(item.getName());
            try {
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(filename));
                Image originalImage = originalIcon.getImage();
    
                // Resize the image to fit in the cell
                int cellWidth = 70; // Set the desired cell width
                int cellHeight = 65; // Set the desired cell height
                Image resizedImage = originalImage.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
    
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                imageMap.put(item.getName(), resizedIcon);
            } catch (NullPointerException e) {
                System.err.println("Error loading image for item: " + item.getName());
                e.printStackTrace();
            }
        }
    }    
    
    /**
     * Generates a filename for an item's image based on its name.
     *
     * @param itemName The name of the item to generate the image filename for.
     * @return The filename for the item's image.
     */
    private String getItemImageFilename(String itemName) {
        return itemName.toLowerCase().replace(" ", "_") + "_image.png";
    }

    /**
     * Handles the purchase of a custom product in the special vending machine.
     * Allows the user to select a base milk tea flavor, add add-ons, standalone ingredients,
     * calculate the price, and complete the purchase.
     */
    public void purchaseCustomProduct() {
        // Check if any milk tea flavors are out of stock
        boolean allInStock = true;
        for (Items.ItemSlot slot : itemSlots) {
            if (slot.getItem() == null || slot.getItem().getQuantity() <= 0) {
                allInStock = false;
                break;
            }
        }
    
        // Check if any standalone ingredients are out of stock
        for (Items.Item item : getStandaloneIngredients()) {
            if (item.getQuantity() == 0) {
                allInStock = false;
                break;
            }
        }
    
        // Check if any add-on ingredients are out of stock
        for (Items.Item item : getAddonIngredients()) {
            if (item.getQuantity() == 0) {
                allInStock = false;
                break;
            }
        }
    
        if (!allInStock) {
            StringBuilder outOfStockMessage = new StringBuilder("<html>Out of stock:<br>");
            for (Items.ItemSlot slot : itemSlots) {
                if (slot.getItem() == null || slot.getItem().getQuantity() <= 0) {
                    outOfStockMessage.append("- ").append(slot.getItem().getName()).append("<br>");
                }
            }
            for (Items.Item item : getStandaloneIngredients()) {
                if (item.getQuantity() == 0) {
                    outOfStockMessage.append("- ").append(item.getName()).append("<br>");
                }
            }
            for (Items.Item item : getAddonIngredients()) {
                if (item.getQuantity() == 0) {
                    outOfStockMessage.append("- ").append(item.getName()).append("<br>");
                }
            }
            outOfStockMessage.append("</html>");
            JOptionPane.showMessageDialog(null, outOfStockMessage.toString());
            return;
        }
    
        // Select base milk tea flavor
        Object[] baseOptions = new Object[itemSlots.length];
        for (int i = 0; i < itemSlots.length; i++) {
            Items.ItemSlot slot = itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null) {
                baseOptions[i] = item.getName();
            } else {
                baseOptions[i] = "Empty";
            }
        }
    
        // Customize the JOptionPane appearance
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
        int baseIndex = JOptionPane.showOptionDialog(null,
                "Select base milk tea flavor:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                baseOptions,
                baseOptions[0]);
    
        if (baseIndex < 0 || baseIndex >= itemSlots.length) {
            // Invalid choice for base milk tea flavor
            resetCustomUIManager();
            return;
        }
    
        Items.ItemSlot baseSlot = itemSlots[baseIndex];
        Items.Item baseItem = baseSlot.getItem();
        if (baseItem == null || baseItem.getQuantity() <= 0) {
            // The selected base milk tea flavor is out of stock
            resetCustomUIManager();
            return;
        }
    
        chooseBase(baseItem);
    
        // Reset the customizations for the next dialog
        resetCustomUIManager();
    
        // Add add-ons
        List<Items.Item> chosenAddons = new ArrayList<>();
        selectAddons(this, chosenAddons);

        // Add standalone ingredients
        List<Items.Item> chosenStandaloneIngredients = new ArrayList<>();
        selectStandaloneIngredients(this, chosenStandaloneIngredients);
    
        // Calculate price and transaction summary
        double price = baseItem.getPrice();
        StringBuilder transactionSummary = new StringBuilder("<html>Transaction Summary:<br>");
        transactionSummary.append("<b>Base: </b>").append(baseItem.getName()).append("<br>");
        transactionSummary.append("<b>Add-ons: </b>");
        for (Items.Item ingredient : chosenAddons) {
            transactionSummary.append(ingredient.getName()).append(", ");
            price += ingredient.getPrice();
        }
        transactionSummary.append("<br><b>Standalone Ingredients: </b>");
        for (Items.Item ingredient : chosenStandaloneIngredients) {
            transactionSummary.append(ingredient.getName()).append(", ");
            price += ingredient.getPrice();
        }
        transactionSummary.append("<br><b>Calories: </b>").append(calculateCalories()).append("<br>");
        transactionSummary.append("<b>Total Price: PHP </b>").append(price).append("<br>");
    
        // Handle payment
        double change = handlePayment(null, price); // We pass null for the frame as it is not required in the summary message.
        if (change >= 0) {
            // Dispense item
            dispenseItem(null, baseItem); // We pass null for the frame as it is not required in the summary message.
            
            // Finalize purchase
            transactionSummary.append("</html>");
            displayPreparationSteps();
            JOptionPane.showMessageDialog(null, transactionSummary.toString());
        } else {
            // Transaction cancelled
            resetCustomUIManager();
            return;
        }
    }
    
    /**
     * Handles the selection of add-on ingredients for the custom product.
     *
     * @param specialVendingMachine The instance of the SpecialVendingMachine.
     * @param chosenAddons          The list to store the chosen add-on ingredients.
     */
    public void selectAddons(SpecialVendingMachine specialVendingMachine, List<Items.Item> chosenAddons) {
        List<Items.Item> addonIngredients = specialVendingMachine.getAddonIngredients();
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        for (Items.Item item : addonIngredients) {
            JCheckBox checkBox = new JCheckBox(item.getName());
            checkBox.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(checkBox);
        }
    
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(300, 300));
    
        // Customize the JOptionPane appearance
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
        int result = JOptionPane.showConfirmDialog(
            null,
            scrollPane,
            "Choose Addons",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result == JOptionPane.OK_OPTION) {
        // Process the selected addons
        chosenAddons.clear();

        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    for (Items.Item item : addonIngredients) {
                        if (item.getName().equals(checkBox.getText())) {
                            chosenAddons.add(item);
                        }
                    }
                }
            }
        }
    
            StringBuilder sb = new StringBuilder();
            sb.append("<html>Chosen addons:<br>");
            for (Items.Item ingredient : specialVendingMachine.chosenIngredients) {
                sb.append("- ").append(ingredient.getName()).append("<br>");
            }
            sb.append("</html>");
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    
        resetCustomUIManager();
    }

    /**
     * Handles the selection of standalone ingredients for the custom product.
     *
     * @param specialVendingMachine       The instance of the SpecialVendingMachine.
     * @param chosenStandaloneIngredients The list to store the chosen standalone ingredients.
     */
    public void selectStandaloneIngredients(SpecialVendingMachine specialVendingMachine, List<Items.Item> chosenStandaloneIngredients) {
        List<Items.Item> standaloneIngredients = specialVendingMachine.getStandaloneIngredients();

        boolean exitToppings = false;
        while (!exitToppings) {
            List<Items.Item> availableStandaloneIngredients = standaloneIngredients.stream()
                    .filter(item -> item.getQuantity() > 0)
                    .collect(Collectors.toList());

            if (availableStandaloneIngredients.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All standalone ingredients are currently out of stock.");
                return;
            }

            Object[] toppingOptions = new Object[availableStandaloneIngredients.size() + 1];
            for (int i = 0; i < availableStandaloneIngredients.size(); i++) {
                Items.Item item = availableStandaloneIngredients.get(i);
                toppingOptions[i] = item.getName();
            }
            toppingOptions[availableStandaloneIngredients.size()] = "Done";

            UIManager.put("OptionPane.background", new Color(255, 255, 230));
            UIManager.put("Panel.background", new Color(255, 255, 230));
            UIManager.put("Button.background", new Color(255, 204, 204));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

            int toppingIndex = JOptionPane.showOptionDialog(null,
                    "Select Additional Topping:",
                    "Vending Machine",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    toppingOptions,
                    toppingOptions[0]);

            resetCustomUIManager();

            if (toppingIndex == availableStandaloneIngredients.size()) {
                exitToppings = true;
            } else {
                Items.Item toppingItem = availableStandaloneIngredients.get(toppingIndex);
                chosenStandaloneIngredients.add(toppingItem);
            }
        }

        // Display the chosen ingredients summary after the selection is done
        if (!chosenStandaloneIngredients.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<html>Chosen additional toppings:<br>");
            for (Items.Item ingredient : chosenStandaloneIngredients) {
                sb.append("- ").append(ingredient.getName()).append("<br>");
            }
            sb.append("</html>");
            JOptionPane.showMessageDialog(null, sb.toString());
        }

        resetCustomUIManager();
    }

    /**
     * Handles the purchase of an individual ingredient from the special vending machine.
     */
    public void purchaseIngredient() {
        // Check if the vending machine is a SpecialVendingMachine
        if (this instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) this;
            List<Items.Item> standaloneIngredients = specialVendingMachine.getStandaloneIngredients();
    
            // Select standalone ingredient
            Object[] standaloneOptions = new Object[standaloneIngredients.size()];
            for (int i = 0; i < standaloneIngredients.size(); i++) {
                Items.Item item = standaloneIngredients.get(i);
                standaloneOptions[i] = item.getName();
            }
    
            // Customize the JOptionPane appearance
            UIManager.put("OptionPane.background", new Color(255, 255, 230));
            UIManager.put("Panel.background", new Color(255, 255, 230));
            UIManager.put("Button.background", new Color(255, 204, 204));
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
    
            int standaloneIndex = JOptionPane.showOptionDialog(null,
                    "Select standalone ingredient:",
                    "Vending Machine",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    standaloneOptions,
                    standaloneOptions[0]);
    
            // Reset the customizations for the next dialog
            resetCustomUIManager();
    
            if (standaloneIndex >= 0 && standaloneIndex < standaloneIngredients.size()) {
                Items.Item standaloneItem = standaloneIngredients.get(standaloneIndex);
    
                // Check if selected ingredient is in stock
                if (standaloneItem.getQuantity() == 0) {
                    JOptionPane.showMessageDialog(null, standaloneItem.getName() + " is out of stock. Transaction cancelled.");
                    return;
                }
    
                // Handle payment
                double change = handlePayment(null, standaloneItem.getPrice()); // We pass null for the frame as it is not required in this method.
                if (change >= 0) {
                    // Dispense item
                    dispenseItem(null, standaloneItem); // We pass null for the frame as it is not required in this method.
                }
            }
        } else {
            view.displayInvalidChoice();
        }
    }
}

