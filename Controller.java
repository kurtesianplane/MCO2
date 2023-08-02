<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Controller class that acts as an intermediary between the Model and View classes.
 * It handles user interactions, creates and manages instances of vending machines,
 * and provides methods to test vending machine features.
 */
public class Controller {
    private Model model;
    private View view;
    private JFrame frame;
    private VendingMachineDisplay display;
    private VendingMachinePurchase purchase;
    private VendingMachineMaintenance maintenance;

    /**
     * Constructs a new Controller with the provided Model and View instances.
     *
     * @param model The Model instance representing the underlying data and logic.
     * @param view  The View instance responsible for user interface and interactions.
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.frame = new JFrame();
        this.display = null;
        this.purchase = null;
        this.maintenance = null;
    }

    /**
     * Runs the vending machine application and displays the main menu.
     * It handles user choices, creates vending machines, and initiates testing if needed.
     */
    public void run() {
        boolean exit = false;
        boolean showMenu = true;
        boolean testing = false;
        while (!exit) {
            int choice = 0;
            if (showMenu) {
                choice = view.displayMenu();
            }
            switch (choice) {
                case 1:
                    createRegularVendingMachine();
                    break;
                case 2:
                    createSpecialVendingMachine();
                    break;
                case 3:
                    testVendingMachine();
                    showMenu = false;
                    testing = true;
                    break;
                case 4:
                    int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        exit = true;
                        System.exit(0);
                    }
                    break;
                default:
                    if (!testing) {
                        view.displayInvalidChoice();
                    }
            }
            if (testing && !exit) {
                continue;
            } else {
                // reset testing and showMenu variables
                testing = false;
                showMenu = true;
            }
        }
    }

     /**
     * Method to test the features of the vending machine.
     * It allows users to test different functionalities of the vending machine.
     */
    private void testVendingMachine() {
        if (model.getVendingMachine() == null) {
            view.displayNoVendingMachine();
            return; // added return statement to exit method and return to the main menu
        }

        boolean exit = false;
        while (!exit) {
            int choice;
            boolean isSpecialVendingMachine = model.getVendingMachine() instanceof SpecialVendingMachine;
            if (isSpecialVendingMachine) {
                choice = view.displayTestMenu(true); // Pass true to indicate SpecialVendingMachine
            } else {
                choice = view.displayTestMenu(false); // Pass false to indicate Regular Vending Machine
            }

            switch (choice) {
                case 1:
                    testVendingFeatures();
                    break;
                case 2:
                    testMaintenanceFeatures();
                    break;
                case 3:
                    if (isSpecialVendingMachine) {
                        testCustomProductFeatures();
                    } else {
                        exit = true;
                    }
                    break;
                case 4:
                    exit = true;
                    return;
                default:
                    view.displayInvalidChoice();
            }
        }
    }

    /**
     * Creates a new Regular Vending Machine and sets up its initial products and displays.
     * It also creates instances of VendingMachineDisplay, VendingMachinePurchase, and VendingMachineMaintenance
     * to interact with the created vending machine.
     */
    private void createRegularVendingMachine() {
        model.createRegularVendingMachine();
        VendingMachine vendingMachine = model.getVendingMachine();
        VendingMachineProducts.addDefaultProducts(vendingMachine);
        display = new VendingMachineDisplay(model.getVendingMachine());
        purchase = new VendingMachinePurchase(model.getVendingMachine());
        maintenance = new VendingMachineMaintenance(model.getVendingMachine());

        // Customize the JOptionPane appearance for the message dialog
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

        JOptionPane.showMessageDialog(frame, "A Regular Vending Machine has been created.", "Vending Machine", JOptionPane.INFORMATION_MESSAGE);
        resetCustomUIManager();
    }

    /**
     * Creates a new Special Vending Machine and sets up its initial products and displays.
     * It also creates instances of VendingMachineDisplay, VendingMachinePurchase, and VendingMachineMaintenance
     * to interact with the created vending machine.
     */
    private void createSpecialVendingMachine() {
        double initialCash = 0.0; // set initial cash balance
        model.createSpecialVendingMachine(initialCash);
        VendingMachine vendingMachine = model.getVendingMachine();
        VendingMachineProducts.addDefaultProducts(vendingMachine);
        display = new VendingMachineDisplay(model.getVendingMachine());
        purchase = new VendingMachinePurchase(model.getVendingMachine());
        maintenance = new VendingMachineMaintenance(model.getVendingMachine());

        // Customize the JOptionPane appearance for the message dialog
        UIManager.put("OptionPane.background", new Color(255, 255, 230));
        UIManager.put("Panel.background", new Color(255, 255, 230));
        UIManager.put("Button.background", new Color(255, 204, 204));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));

        JOptionPane.showMessageDialog(frame, "A Special Vending Machine has been created.", "Vending Machine", JOptionPane.INFORMATION_MESSAGE);
        resetCustomUIManager();
    }

     /**
     * Tests the vending machine features, such as displaying available items and making purchases.
     * It also allows users to exit or go back to the previous menu.
     */
    private void testVendingFeatures() {
        boolean exit = false;
        while (!exit) {
            int choice = view.displayVendingFeaturesMenu();
            switch (choice) {
                case 1:
                    display.displayItems();
                    break;
                case 2:
                    purchase.purchaseItem(frame);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    view.displayInvalidChoice();
            }
        }
    }

    /**
     * Tests the maintenance features of the vending machine, such as restocking items and setting prices.
     * It also allows users to exit or go back to the previous menu.
     */
    private void testMaintenanceFeatures() {
        boolean exit = false;
        while (!exit) {
            int choice = view.displayMaintenanceFeaturesMenu(model.getVendingMachine() instanceof SpecialVendingMachine);
            switch (choice) {
                case 1:
                    maintenance.restockItem(frame);
                    break;
                case 2:
                    maintenance.setItemPrice(frame);
                    break;
                case 3:
                    maintenance.replenishCash(frame);
                    break;
                case 4:
                    maintenance.displayTransactions();
                    break;
                case 5:
                    if (model.getVendingMachine() instanceof SpecialVendingMachine) {
                        maintenance.restockIngredients(frame);
                        break;
                    } else {
                        exit = true;
                        break;
                    }
                case 6:
                    exit = true;
                    break;
                default:
                    view.displayInvalidChoice();
            }
        }
    }

    /**
     * Tests the custom product features of the Special Vending Machine, such as creating and purchasing custom products.
     * It also allows users to exit or go back to the previous menu.
     */
    private void testCustomProductFeatures() {
        if (model.getVendingMachine() instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) model.getVendingMachine();
            Map<String, ImageIcon> imageMap;
            boolean exit = false;
            while (!exit) {
                int choice = view.displayCustomProductFeaturesMenu();
                switch (choice) {
                    case 1:
                        specialVendingMachine.displayCustomProduct();
                        break;
                    case 2:
                        specialVendingMachine.purchaseCustomProduct();
                        break;
                    case 3:
                        specialVendingMachine.purchaseIngredient();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        view.displayInvalidChoice();
                }
            }
        } else {
            view.displayInvalidChoice();
        }
    }    

    /**
     * Resets the custom UI manager settings to default values.
     * It resets any customizations made to the UIManager for JOptionPane appearance.
     */
    private void resetCustomUIManager() {
        // Reset any customizations made to the UIManager
        UIManager.put("OptionPane.background", UIManager.get("OptionPane.background.default"));
        UIManager.put("Panel.background", UIManager.get("Panel.background.default"));
        UIManager.put("Button.background", UIManager.get("Button.background.default"));
        UIManager.put("Button.font", UIManager.get("Button.font.default"));
        UIManager.put("OptionPane.messageFont", UIManager.get("OptionPane.messageFont.default"));
    }
}
=======
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controller {
    private Model model;
    private View view;
    private JFrame frame;
    private VendingMachineDisplay display;
    private VendingMachinePurchase purchase;
    private VendingMachineMaintenance maintenance;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.frame = new JFrame();
        this.display = null;
        this.purchase = null;
        this.maintenance = null;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        boolean showMenu = true;
        boolean testing = false;
        while (!exit) {
            int choice = 0;
            if (showMenu) {
                choice = view.displayMenu();
            }
            switch (choice) {
                case 1:
                    model.createRegularVendingMachine();
                    VendingMachine vendingMachine = model.getVendingMachine();
                    VendingMachineProducts.addDefaultProducts(vendingMachine);
                    display = new VendingMachineDisplay(model.getVendingMachine());
                    purchase = new VendingMachinePurchase(model.getVendingMachine());
                    maintenance = new VendingMachineMaintenance(model.getVendingMachine());
                    JOptionPane.showMessageDialog(frame, "A Regular Vending Machine has been created.");
                    break;
                case 2:
                    model.createSpecialVendingMachine(0.1); // pass discount as argument
                    display = new VendingMachineDisplay(model.getVendingMachine());
                    purchase = new VendingMachinePurchase(model.getVendingMachine());
                    maintenance = new VendingMachineMaintenance(model.getVendingMachine());
                    JOptionPane.showMessageDialog(frame, "A Special Vending Machine has been created.");
                    break;
                case 3:
                    testVendingMachine();
                    showMenu = false;
                    testing = true;
                    break;
                case 4:
                    int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        exit = true;
                    }
                    break;
                default:
                    if (!testing) {
                        view.displayInvalidChoice();
                    }
            }
            if (testing) {
                continue;
            }
        }
        scanner.close();
    }
    
    private void testVendingMachine() {
        if (model.getVendingMachine() == null) {
            view.displayNoVendingMachine();
            return;
        }
    
        boolean exit = false;
        while (!exit) {
            int choice = view.displayTestMenu();
            switch (choice) {
                case 1:
                    testVendingFeatures();
                    break;
                case 2:
                    testMaintenanceFeatures();
                    break;
                case 3:
                    if (model.getVendingMachine() instanceof SpecialVendingMachine) {
                        testCustomProductFeatures();
                    } else {
                        view.displayInvalidChoice();
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    view.displayInvalidChoice();
            }
        }
    }
    
    private void testVendingFeatures() {
        boolean exit = false;
        while (!exit) {
            int choice = view.displayVendingFeaturesMenu();
            switch (choice) {
                case 1:
                    display.displayItems();
                    break;
                case 2:
                    purchase.purchaseItem(frame);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    view.displayInvalidChoice();
            }
        }
    }
    
    private void testMaintenanceFeatures() {
        boolean exit = false;
        while (!exit) {
            int choice = view.displayMaintenanceFeaturesMenu();
            switch (choice) {
                case 1:
                    maintenance.restockItem(frame);
                    break;
                case 2:
                    maintenance.setItemPrice(frame);
                    break;
                case 3:
                    maintenance.replenishCash(frame);
                    break;
                case 4:
                    display.displayTransactions();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    view.displayInvalidChoice();
            }
        }
    }
    
    private void testCustomProductFeatures() {
        if (model.getVendingMachine() instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) model.getVendingMachine();
            specialVendingMachine.testCustomProductFeatures(frame);
        } else {
            view.displayInvalidChoice();
        }
    }    
}
>>>>>>> b145b2538ff02a0f9884fd9e4fdacd0282577dfd
