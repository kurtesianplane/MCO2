import java.util.List;

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

    private void createRegularVendingMachine() {
        model.createRegularVendingMachine();
        VendingMachine vendingMachine = model.getVendingMachine();
        VendingMachineProducts.addDefaultProducts(vendingMachine);
        display = new VendingMachineDisplay(model.getVendingMachine());
        purchase = new VendingMachinePurchase(model.getVendingMachine());
        maintenance = new VendingMachineMaintenance(model.getVendingMachine());
        JOptionPane.showMessageDialog(frame, "A Regular Vending Machine has been created.");
    }

    private void createSpecialVendingMachine() {
        double initialCash = 0.0; // set initial cash balance
        model.createSpecialVendingMachine(initialCash);
        VendingMachine vendingMachine = model.getVendingMachine();
        VendingMachineProducts.addDefaultProducts(vendingMachine);
        display = new VendingMachineDisplay(model.getVendingMachine());
        purchase = new VendingMachinePurchase(model.getVendingMachine());
        maintenance = new VendingMachineMaintenance(model.getVendingMachine());
        JOptionPane.showMessageDialog(frame, "A Special Vending Machine has been created.");
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
                    maintenance.displayTransactions();
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
        boolean exit = false;
        while (!exit) {
            int choice = view.displayCustomProductFeaturesMenu();
            switch (choice) {
                case 1:
                    displayCustomProduct();
                    break;
                case 2:
                    purchaseCustomProduct();
                    break;
                case 3:
                    purchaseIngredient();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    view.displayInvalidChoice();
            }
        }
    }

    private void displayCustomProduct() {
        if (model.getVendingMachine() instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) model.getVendingMachine();
            List<Items.Item> standaloneIngredients = specialVendingMachine.getStandaloneIngredients();
            List<Items.Item> addonIngredients = specialVendingMachine.getAddonIngredients();

            StringBuilder sb = new StringBuilder();
            sb.append("Standalone Ingredients:\n");
            for (Items.Item item : standaloneIngredients) {
                sb.append("- " + item.getName() + ": " + item.getPrice() + ", " + item.getCalories() + " calories\n");
            }
            sb.append("\nAdd-on Ingredients:\n");
            for (Items.Item item : addonIngredients) {
                sb.append("- " + item.getName() + ": " + item.getPrice() + ", " + item.getCalories() + " calories\n");
            }

            view.displayItems(sb.toString());
        } else {
            view.displayInvalidChoice();
        }
    }

    private void purchaseCustomProduct() {
        if (model.getVendingMachine() instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) model.getVendingMachine();
            List<Items.Item> standaloneIngredients = specialVendingMachine.getStandaloneIngredients();
            List<Items.Item> addonIngredients = specialVendingMachine.getAddonIngredients();

            // select base milk tea flavor
            Object[] baseOptions = new Object[model.getVendingMachine().itemSlots.length];
            for (int i = 0; i < model.getVendingMachine().itemSlots.length; i++) {
                Items.ItemSlot slot = model.getVendingMachine().itemSlots[i];
                Items.Item item = slot.getItem();
                if (item != null) {
                    baseOptions[i] = item.getName();
                } else {
                    baseOptions[i] = "Empty";
                }
            }
            int baseIndex = JOptionPane.showOptionDialog(null,
                "Select base milk tea flavor:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                baseOptions,
                baseOptions[0]);
            Items.ItemSlot baseSlot = model.getVendingMachine().itemSlots[baseIndex];
            Items.Item baseItem = baseSlot.getItem();
            specialVendingMachine.chooseBase(baseItem);

            // add add-ons
            boolean exitAddons = false;
            while (!exitAddons) {
                Object[] addonOptions = new Object[addonIngredients.size() + 1];
                for (int i = 0; i < addonIngredients.size(); i++) {
                    Items.Item item = addonIngredients.get(i);
                    addonOptions[i] = item.getName();
                }
                addonOptions[addonIngredients.size()] = "Done";
                int addonIndex = JOptionPane.showOptionDialog(null,
                    "Select add-on:",
                    "Vending Machine",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    addonOptions,
                    addonOptions[0]);
                if (addonIndex == addonIngredients.size()) {
                    exitAddons = true;
                } else {
                    Items.Item addonItem = addonIngredients.get(addonIndex);
                    specialVendingMachine.chooseIngredient(addonItem);
                }
            }

            // add standalone ingredients
            boolean exitStandalone = false;
            while (!exitStandalone) {
                Object[] standaloneOptions = new Object[standaloneIngredients.size() + 1];
                for (int i = 0; i < standaloneIngredients.size(); i++) {
                    Items.Item item = standaloneIngredients.get(i);
                    standaloneOptions[i] = item.getName();
                }
                standaloneOptions[standaloneIngredients.size()] = "Done";
                int standaloneIndex = JOptionPane.showOptionDialog(null,
                    "Select standalone ingredient:",
                    "Vending Machine",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    standaloneOptions,
                    standaloneOptions[0]);
                if (standaloneIndex == standaloneIngredients.size()) {
                    exitStandalone = true;
                } else {
                    Items.Item standaloneItem = standaloneIngredients.get(standaloneIndex);
                    specialVendingMachine.chooseIngredient(standaloneItem);
                }
            }

            // calculate price
            double price = baseItem.getPrice();
            for (Items.Item ingredient : specialVendingMachine.chosenIngredients) {
                price += ingredient.getPrice();
            }

            // handle payment
            double change = specialVendingMachine.handlePayment(frame, price);
            if (change >= 0) {
                // dispense item
                specialVendingMachine.dispenseItem(frame, baseItem);

                // finalize purchase
                int calories = specialVendingMachine.calculateCalories();
                String preparationSteps = specialVendingMachine.displayPreparationSteps();
                String message = "<html>Custom Milk Tea:<br>" +
                                 "- Base: " + baseItem.getName() + "<br>" +
                                 "- Add-ons: ";
                for (Items.Item ingredient : specialVendingMachine.chosenIngredients) {
                    message += ingredient.getName() + ", ";
                }
                message += "<br>- Calories: " + calories + "<br>";
                message += preparationSteps;
                message += "</html>";
                JOptionPane.showMessageDialog(null, message);
            }
        } else {
            view.displayInvalidChoice();
        }
    }

    private void purchaseIngredient() {
        if (model.getVendingMachine() instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) model.getVendingMachine();
            List<Items.Item> standaloneIngredients = specialVendingMachine.getStandaloneIngredients();

            // select standalone ingredient
            Object[] standaloneOptions = new Object[standaloneIngredients.size()];
            for (int i = 0; i < standaloneIngredients.size(); i++) {
                Items.Item item = standaloneIngredients.get(i);
                standaloneOptions[i] = item.getName();
            }
            int standaloneIndex = JOptionPane.showOptionDialog(null,
                "Select standalone ingredient:",
                "Vending Machine",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                standaloneOptions,
                standaloneOptions[0]);
            Items.Item standaloneItem = standaloneIngredients.get(standaloneIndex);

            // handle payment
            double change = specialVendingMachine.handlePayment(frame, standaloneItem.getPrice());
            if (change >= 0) {
                // dispense item
                specialVendingMachine.dispenseItem(frame, standaloneItem);
            }
        } else {
            view.displayInvalidChoice();
        }
    }
}
