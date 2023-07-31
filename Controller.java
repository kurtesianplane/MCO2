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
