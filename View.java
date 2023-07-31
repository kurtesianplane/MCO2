import javax.swing.*;

public class View {
    public int displayMenu() {
        Object[] options = {"Create a Regular Vending Machine",
                            "Create a Special Vending Machine",
                            "Test a Vending Machine",
                            "Exit"};
        int choice = JOptionPane.showOptionDialog(null,
            "Vending Machine Menu:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        return choice + 1;
    }

    public int displayTestMenu() {
        Object[] options = {"Vending Machine Features Test",
                            "Maintenance Features Test",
                            "Custom Product Features Test",
                            "Exit"};
        int choice = JOptionPane.showOptionDialog(null,
            "Test Vending Machine Menu:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        return choice + 1;
    }

    public int displayVendingFeaturesMenu() {
        Object[] options = {"Display Items",
                            "Purchase Item",
                            "Exit"};
        int choice = JOptionPane.showOptionDialog(null,
            "Vending Machine Features Test Menu:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        return choice + 1;
    }

    public int displayMaintenanceFeaturesMenu() {
        Object[] options = {"Restock Item",
                            "Set Item Price",
                            "Replenish Cash",
                            "Display Transactions",
                            "Exit"};
        int choice = JOptionPane.showOptionDialog(null,
            "Maintenance Features Test Menu:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        return choice + 1;
    }

    public int displayCustomProductFeaturesMenu() {
        Object[] options = {"Display Customization Items",
                            "Purchase Custom Product",
                            "Exit"};
        int choice = JOptionPane.showOptionDialog(null,
            "Custom Product Features Test Menu:",
            "Vending Machine",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        return choice + 1;
    }

    public void displayNoVendingMachine() {
        JOptionPane.showMessageDialog(null, "No vending machine has been created yet.");
    }

    public void displayInvalidChoice() {
        JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
    }

    public void displayItems(String items) {
        JTextArea textArea = new JTextArea(items);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(300, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Vending Machine Items", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayPurchaseResult(String result) {
        JTextArea textArea = new JTextArea(result);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(300, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Purchase Result", JOptionPane.INFORMATION_MESSAGE);
    }
}
