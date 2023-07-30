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
