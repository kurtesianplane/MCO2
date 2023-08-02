<<<<<<< HEAD
/**
 * The Model class represents a model for a Vending Machine application.
 * It manages the creation and retrieval of vending machines.
 */
public class Model {
    private VendingMachine vendingMachine;

    /**
     * Constructs a new Model object with no initial vending machine.
     */
    public Model() {
        vendingMachine = null;
    }

    /**
     * Retrieves the current vending machine.
     *
     * @return The current vending machine instance, or null if no vending machine has been created yet.
     */
    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    /**
     * Creates a new regular vending machine.
     * If a vending machine already exists, it will be replaced by the new regular vending machine.
     */
    public void createRegularVendingMachine() {
        vendingMachine = new VendingMachine();
    }

    /**
     * Creates a new special vending machine with a given discount.
     * If a vending machine already exists, it will be replaced by the new special vending machine.
     *
     * @param discount The discount percentage to be applied to the special vending machine.
     */
    public void createSpecialVendingMachine(double discount) {
        vendingMachine = new SpecialVendingMachine(discount);
    }
}
=======
public class Model {
    private VendingMachine vendingMachine;

    public Model() {
        vendingMachine = null;
    }

    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    public void createRegularVendingMachine() {
        vendingMachine = new VendingMachine();
    }

    public void createSpecialVendingMachine(double discount) {
        vendingMachine = new SpecialVendingMachine(discount);
    }
}
>>>>>>> b145b2538ff02a0f9884fd9e4fdacd0282577dfd
