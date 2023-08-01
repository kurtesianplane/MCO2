import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VendingMachineDisplay {
    private VendingMachine vendingMachine;

    public VendingMachineDisplay(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void displayItems() {
        // create table model and set column names
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Slot", "Name", "Price", "Calories"});

        // add rows to table model
        for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
            Items.ItemSlot slot = vendingMachine.itemSlots[i];
            Items.Item item = slot.getItem();
            if (item != null && !item.isIngredient()) {
                model.addRow(new Object[]{i, item.getName(), item.getPrice(), item.getCalories()});
            }
        }

        // create table and set model
        JTable table = new JTable(model);

        // create scroll pane and add table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new java.awt.Dimension(300, 300));

        // display table in JOptionPane
        JOptionPane.showMessageDialog(null, scrollPane, "Vending Machine Items", JOptionPane.INFORMATION_MESSAGE);
    }
}
