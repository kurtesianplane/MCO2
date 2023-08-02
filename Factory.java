<<<<<<< HEAD
/**
 * The entry point of the vending machine application.
 * It initializes the Model, View, and Controller, and starts the vending machine.
 */
public class Factory {
    /**
     * The main method to start the vending machine application.
     * It initializes the Model, View, and Controller, and starts the vending machine.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.run();
    }
=======
public class Factory {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.run();
    }
>>>>>>> b145b2538ff02a0f9884fd9e4fdacd0282577dfd
}