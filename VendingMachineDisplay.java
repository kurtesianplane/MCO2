import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The VendingMachineDisplay class is responsible for displaying the items in a vending machine
 * in a graphical user interface (GUI) using a JTable. It loads images for the items and presents
 * them along with their stock, name, price, and calories in a tabular format for easy visualization.
 */
public class VendingMachineDisplay {
    private VendingMachine vendingMachine;
    private Map<String, ImageIcon> imageMap;

    /**
     * Constructs a new VendingMachineDisplay object with the given vending machine instance.
     *
     * @param vendingMachine The VendingMachine instance representing the vending machine to display.
     */
    public VendingMachineDisplay(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.imageMap = new HashMap<>();
    }

    /**
     * Displays the items in the vending machine using a JTable with images and relevant information.
     */
    public void displayItems() {

        loadImages();

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? ImageIcon.class : Object.class;
            }
        };
        model.setColumnIdentifiers(new Object[]{"Photo", "Stocks", "Name", "Price", "Calories"});

        for (Items.ItemSlot slot : vendingMachine.itemSlots) {
            Items.Item item = slot.getItem();
            if (item != null && !item.isIngredient()) {
                ImageIcon icon = imageMap.get(item.getName());
                model.addRow(new Object[]{icon, item.getQuantity(), item.getName(), item.getPrice(), item.getCalories()});
            }
        }

        JTable table = new JTable(model);
        table.setRowHeight(100);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showOptionDialog(
                null,
                panel,
                "Vending Machine Items",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{},
                null
        );
    }

    // Method to load the images and store them in the image map
    private void loadImages() {
        for (int i = 0; i < vendingMachine.itemSlots.length; i++) {
            Items.Item item = vendingMachine.itemSlots[i].getItem();
            if (item != null && !item.isIngredient()) {
                String filename = "/products/" + getItemImageFilename(item.getName());
                try {
                    ImageIcon originalIcon = new ImageIcon(getClass().getResource(filename));
                    Image originalImage = originalIcon.getImage();
    
                    
                    int cellWidth = 70;
                    int cellHeight = 65;
                    Image resizedImage = originalImage.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
    
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    imageMap.put(item.getName(), resizedIcon);
                } catch (NullPointerException e) {
                    System.err.println("Error loading image for item: " + item.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to get the image filename based on the item name (customize this as per your image filenames)
    private String getItemImageFilename(String itemName) {
        return itemName.toLowerCase().replace(" ", "_") + "_image.png";
    }
}
