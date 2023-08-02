<<<<<<< HEAD
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

/**
 * The View class represents the graphical user interface (GUI) for a Vending Machine application.
 * It provides methods to display various menus and user interactions using Swing components.
 */
public class View {
    private JFrame frame;

    /**
     * Constructs a new View object, initializing the main frame of the application.
     * The frame is set up with a background image, and its default properties are set.
     */
    public View() {
        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Vending Machine Factory");
        frame.setSize(400, 400);

        try {
            URL imageUrl = getClass().getResource("background.jpg");
            if (imageUrl != null) {
                BufferedImage img = ImageIO.read(imageUrl);
                BackgroundPanel backgroundPanel = new BackgroundPanel(img, BackgroundPanel.SCALED);
                frame.setContentPane(backgroundPanel);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Center the window on the desktop
        frame.setLocationRelativeTo(null);
    }

    /**
     * Displays the main menu to the user and waits for their choice.
     *
     * @return An integer representing the user's menu choice.
     *         1 - Create a regular vending machine
     *         2 - Create a special vending machine
     *         3 - Test current vending machine
     *         4 - Exit the program
     */
    public int displayMenu() {
        // Create the frame
        JFrame frame = new JFrame();
        frame.setTitle("Vending Machine Factory");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Create the title label
        JLabel title = new JLabel("Vending Machine Factory", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(title, BorderLayout.NORTH);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        String[] options = {
                "CREATE A REGULAR VENDING MACHINE",
                "CREATE A SPECIAL VENDING MACHINE",
                "TEST CURRENT VENDING MACHINE",
                "EXIT"
        };
        String[] descriptions = {
                "Creates a regular vending machine.",
                "Creates a vending machine with extra features.",
                "Tests the features of the vending machine you just made.",
                "Exits the program."
        };
        Color[] colors = {
                new Color(255, 179, 186),
                new Color(255, 223, 186),
                new Color(255, 255, 186),
                new Color(186, 255, 201)
        };

        // create an array to hold the buttons
        JButton[] buttons = new JButton[options.length];

        // create a variable to hold the user's choice
        final int[] choice = new int[1];
        choice[0] = -1;

        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton("<html><div style='text-align:center;'><font size='5'><b>" + options[i] + "</b></font><br>" + descriptions[i] + "</div></html>");
            button.setBackground(colors[i]);
            // add an ActionListener to the button
            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // set the choice variable to the index of the clicked button
                    choice[0] = index;
                    synchronized (frame) {
                        frame.notify();
                    }
                }
            });

            buttons[i] = button;
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);

        // show the frame
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });

        // wait for user input
        synchronized (frame) {
            while (choice[0] == -1) {
                try {
                    frame.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // dispose of the frame
        frame.dispose();

        // reset the value of the choice variable back to -1
        int result = choice[0] + 1;
        choice[0] = -1;

        // return the user's choice
        return result;
    }

    /**
     * Displays a message to inform the user that no vending machine has been created yet.
     */
    public void displayNoVendingMachine() {
        JOptionPane.showMessageDialog(frame, "No vending machine has been created yet.");
    }

    /**
     * Displays the test menu to the user and waits for their choice.
     *
     * @param isSpecialVendingMachine A boolean indicating whether the vending machine is special or regular.
     * @return An integer representing the user's menu choice.
     *         1 - Vending features
     *         2 - Maintenance features
     *         3 - Special features (only available for special vending machines)
     *         4 - Exit the program
     */
    public int displayTestMenu(boolean isSpecialVendingMachine) {
        // Create the frame
        JFrame frame = new JFrame();
        frame.setTitle("Test Menu");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
    
        // Create the title label
        JLabel title = new JLabel("Test Menu", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(title, BorderLayout.NORTH);
    
        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
    
        String[] options;
        String[] descriptions;
        Color[] colors;
    
        if (isSpecialVendingMachine) {
            options = new String[]{
                "VENDING FEATURES",
                "MAINTENANCE FEATURES",
                "SPECIAL FEATURES",
                "EXIT"
            };
            descriptions = new String[]{
                "Tests the features of your vending machine.",
                "Tests the maintenance features of your vending machine.",
                "Tests the special features of your vending machine.",
                "Exits the program."
            };
            colors = new Color[]{
                new Color(255, 179, 186),
                new Color(255, 223, 186),
                new Color(255, 255, 186),
                new Color(186, 255, 201)
            };
        } else {
            options = new String[]{
                "VENDING FEATURES",
                "MAINTENANCE FEATURES",
                "EXIT"
            };
            descriptions = new String[]{
                "Tests the features of your vending machine.",
                "Tests the maintenance features of your vending machine.",
                "Exits the program."
            };
            colors = new Color[]{
                new Color(255, 179, 186),
                new Color(255, 223, 186),
                new Color(186, 255, 201)
            };
        }
    
        // create an array to hold the buttons
        JButton[] buttons = new JButton[options.length];
    
        // create a variable to hold the user's choice
        final int[] choice = new int[1];
        choice[0] = -1;
    
        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton("<html><div style='text-align:center;'><font size='+2'><b>" + options[i] + "</b></font><br>" + descriptions[i] + "</div></html>");
            button.setBackground(colors[i]);
            // add an ActionListener to the button
            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // set the choice variable to the index of the clicked button
                    choice[0] = index;
                    synchronized (frame) {
                        frame.notify();
                    }
                }
            });
    
            buttons[i] = button;
            buttonPanel.add(button);
        }
    
        frame.add(buttonPanel, BorderLayout.CENTER);
    
        // show the frame
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    
        // wait for user input
        synchronized (frame) {
            while (choice[0] == -1) {
                try {
                    frame.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
        // dispose of the frame
        frame.dispose();
    
        // reset the value of the choice variable back to -1
        int result = choice[0] + 1;
        choice[0] = -1;
    
        // return the user's choice
        return result;
    }

    /**
     * Displays a message to inform the user that they have entered an invalid choice.
     */
    public void displayInvalidChoice() {
        JOptionPane.showMessageDialog(frame, "Invalid choice. Please try again.");
    }

    /**
     * Displays the vending features menu to the user and waits for their choice.
     *
     * @return An integer representing the user's menu choice.
     *         1 - Display products
     *         2 - Purchase a product
     *         3 - Exit the menu
     */
    public int displayVendingFeaturesMenu() {
        // Create the frame
        JFrame frame = new JFrame();
        frame.setTitle("Vending Features Menu");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
    
        // Create the title label
        JLabel title = new JLabel("Vending Features Menu", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(title, BorderLayout.NORTH);
    
        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
    
        String[] options = {
                "DISPLAY PRODUCTS",
                "PURCHASE A PRODUCT",
                "EXIT"
        };
        String[] descriptions = {
                "Displays the available items in the vending machine.",
                "Allows you to purchase a product from the vending machine.",
                "Returns to the previous menu."
        };
        Color[] colors = {
                new Color(255, 179, 186),
                new Color(255, 223, 186),
                new Color(255, 255, 186)
        };
    
        // create an array to hold the buttons
        JButton[] buttons = new JButton[options.length];
    
        // create a variable to hold the user's choice
        final int[] choice = new int[1];
        choice[0] = -1;
    
        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton("<html><div style='text-align:center;'><font size='+2'><b>" + options[i] + "</b></font><br>" + descriptions[i] + "</div></html>");
            button.setBackground(colors[i]);
            // add an ActionListener to the button
            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // set the choice variable to the index of the clicked button
                    choice[0] = index;
                    synchronized (frame) {
                        frame.notify();
                    }
                }
            });
    
            buttons[i] = button;
            buttonPanel.add(button);
        }
    
        frame.add(buttonPanel, BorderLayout.CENTER);
    
        // show the frame
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    
        // wait for user input
        synchronized (frame) {
            while (choice[0] == -1) {
                try {
                    frame.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
        // dispose of the frame
        frame.dispose();
    
        // reset the value of the choice variable back to -1
        int result = choice[0] + 1;
        choice[0] = -1;
    
        // return the user's choice
        return result;
    }

    /**
     * Displays the maintenance features menu to the user and waits for their choice.
     *
     * @param isSpecialVendingMachine A boolean representing whether or not the vending machine is a special vending machine.
     * @return An integer representing the user's menu choice.
     *         1 - Restock products
     *         2 - Set product price
     *         3 - Replenish denominations
     *         4 - Display transactions
     *         5 - Restock ingredients
     *         6 - Back
     */
    public int displayMaintenanceFeaturesMenu(boolean isSpecialVendingMachine) {
        JFrame frame = new JFrame();
        frame.setTitle("Maintenance Features Menu");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
    
        JLabel title = new JLabel("Maintenance Features Menu", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(title, BorderLayout.NORTH);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(isSpecialVendingMachine ? 6 : 5, 1));
    
        String[] options;
        String[] descriptions;
        Color[] colors;
    
        if (isSpecialVendingMachine) {
            options = new String[] {
                "RESTOCK PRODUCTS",
                "SET PRODUCT PRICE",
                "REPLENISH DENOMINATIONS",
                "DISPLAY TRANSACTIONS",
                "RESTOCK INGREDIENTS",
                "BACK"
            };
            descriptions = new String[] {
                "Allows you to restock products in the vending machine.",
                "Changes the price of a product in the vending machine.",
                "Replenishes the available denominations in the vending machine.",
                "Shows all the transactions made in the vending machine.",
                "Allows you to restock ingredients for the special features of the vending machine.",
                "Returns to the previous menu."
            };
            colors = new Color[] {
                new Color(255, 179, 186),
                new Color(255, 223, 186),
                new Color(255, 255, 186),
                new Color(186, 255, 201),
                new Color(186, 225, 255),
                new Color(186, 186, 255)
            };
        } else {
            options = new String[] {
                "RESTOCK PRODUCTS",
                "SET PRODUCT PRICE",
                "REPLENISH DENOMINATIONS",
                "DISPLAY TRANSACTIONS",
                "BACK"
            };
            descriptions = new String[] {
                "Allows you to restock products in the vending machine.",
                "Changes the price of a product in the vending machine.",
                "Replenishes the available denominations in the vending machine.",
                "Shows all the transactions made in the vending machine.",
                "Returns to the previous menu."
            };
            colors = new Color[] {
                new Color(255, 179, 186),
                new Color(255, 223, 186),
                new Color(255, 255, 186),
                new Color(186, 255, 201),
                new Color(186, 186, 255) 
            };
        }
    
        JButton[] buttons = new JButton[options.length];
    
        final int[] choice = new int[1];
        choice[0] = -1;
    
        for (int i = 0; i < options.length; i++) { // Changed to iterate through all options
            JButton button = new JButton("<html><div style='text-align:center;'><font size='+0.5'><b>" + options[i] + "</b></font><br>" + descriptions[i] + "</div></html>");
            button.setBackground(colors[i]);
            // add an ActionListener to the button
            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    choice[0] = index;
                    synchronized (frame) {
                        frame.notify();
                    }
                }
            });
    
            buttons[i] = button;
            buttonPanel.add(button);
        }
    
        frame.add(buttonPanel, BorderLayout.CENTER);
    
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    
        synchronized (frame) {
            while (choice[0] == -1) {
                try {
                    frame.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
        frame.dispose();
    
        int result = choice[0] + 1;
        choice[0] = -1;
    
        return result;
    }
    
    /**
     * Displays the custom product features menu to the user and waits for their choice.
     *
     * @return An integer representing the user's menu choice.
     *         1 - Display ingredients
     *         2 - Create a customized product
     *         3 - Purchase an individual ingredient
     *         4 - Back
     */
    public int displayCustomProductFeaturesMenu() {
        // Create the frame
        JFrame frame = new JFrame();
        frame.setTitle("Special Menu");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
    
        // Create the title label
        JLabel title = new JLabel("Special Menu", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(title, BorderLayout.NORTH);
    
        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
    
        String[] options = {
            "DISPLAY INGREDIENTS",
            "CREATE A CUSTOMIZED PRODUCT",
            "PURCHASE AN INDIVIDUAL INGREDIENT",
            "BACK"
        };
        String[] descriptions = {
            "Displays all the ingredients available in the vending machine.",
            "Allows you to purchase a customized product from the vending machine.",
            "Allows you to purchase an individual ingredient from the vending machine.",
            "Returns to the previous menu."
        };
        Color[] colors = {
            new Color(255, 179, 186),
            new Color(255, 223, 186),
            new Color(255, 255, 186),
            new Color(186, 255, 201)
        };
    
        // create a list to hold the buttons
        List<JButton> buttons = new ArrayList<>();
    
        // create a variable to hold the user's choice
        final int[] choice = new int[1];
        choice[0] = -1;
    
        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton("<html><div style='text-align:center;'><font size='+0.5'><b>" + options[i] + "</b></font><br>" + descriptions[i] + "</div></html>");
            button.setBackground(colors[i]);
            // add an ActionListener to the button
            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // set the choice variable to the index of the clicked button
                    choice[0] = index;
                    synchronized (frame) {
                        frame.notify();
                    }
                }
            });
    
            buttons.add(button);
            buttonPanel.add(button);
        }
    
        frame.add(buttonPanel, BorderLayout.CENTER);
    
        // show the frame
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    
        // wait for user input
        synchronized (frame) {
            while (choice[0] == -1) {
                try {
                    frame.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    
        // dispose of the frame
        frame.dispose();
    
        // reset the value of the choice variable back to -1
        int result = choice[0] + 1;
        choice[0] = -1;
    
        // return the user's choice
        return result;
    }

    // Static class for background image
    private static class BackgroundPanel extends JPanel {
        public static final int SCALED = 0;

        private BufferedImage image;
        private int mode;

        public BackgroundPanel(BufferedImage image, int mode) {
            this.image = image;
            this.mode = mode;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                if (mode == SCALED) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g.drawImage(image, 0, 0, null);
                }
            }
        }
    }
}


=======
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
>>>>>>> b145b2538ff02a0f9884fd9e4fdacd0282577dfd
