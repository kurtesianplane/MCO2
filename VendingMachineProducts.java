/**
 * The VendingMachineProducts class provides a static method to add default products to a vending machine.
 * These products include various flavors of milk tea and their corresponding ingredients and addons.
 */
public class VendingMachineProducts {

    /**
     * Adds default products to the given vending machine, including various flavors of milk tea
     * and their corresponding ingredients and addons. If the vending machine is a SpecialVendingMachine,
     * additional standalone and addon ingredients are added to it as well.
     *
     * @param vendingMachine The VendingMachine instance to which default products will be added.
     */
    public static void addDefaultProducts(VendingMachine vendingMachine) {
        // Add regular milktea flavors
        vendingMachine.addItem("Okinawa", 49, 410, "okinawa_image.png");
        vendingMachine.addItem("Wintermelon", 59, 435, "wintermelon_image.png");
        vendingMachine.addItem("Hokkaido", 59, 455, "hokkaido_image.png");
        vendingMachine.addItem("Taro", 65, 455, "taro_image.png");
        vendingMachine.addItem("Hazelnut", 79, 475, "hazelnut_image.png");
        vendingMachine.addItem("Salted Caramel", 89, 510, "salted_caramel_image.png");
        vendingMachine.addItem("Double Dutch", 99, 510, "double_dutch_image.png");
        vendingMachine.addItem("Black Forest", 105, 565, "black_forest_image.png");
        vendingMachine.addItem("Choco Java", 119, 585, "choco_java_image.png");
        vendingMachine.addItem("Matcha Cookie", 119, 605, "matcha_cookie_image.png");
        // If the vending machine is a SpecialVendingMachine, add standalone and addon ingredients
        if (vendingMachine instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) vendingMachine;

            // Add standalone ingredients
            specialVendingMachine.addStandaloneItem("Black Tea", 30, 2, Items.Item.ItemType.STANDALONE_INGREDIENT, "black_tea_image.png");
            specialVendingMachine.addStandaloneItem("Brown Sugar", 100, 2400, Items.Item.ItemType.STANDALONE_INGREDIENT, "brown_sugar_image.png");
            specialVendingMachine.addStandaloneItem("Milk", 30, 120, Items.Item.ItemType.STANDALONE_INGREDIENT, "milk_image.png");
            specialVendingMachine.addStandaloneItem("Water", 20, 0, Items.Item.ItemType.STANDALONE_INGREDIENT, "water_image.png");
            specialVendingMachine.addStandaloneItem("Ice", 10, 0, Items.Item.ItemType.STANDALONE_INGREDIENT, "ice_image.png");

            //Add addon ingredients
            specialVendingMachine.addAddonItem("Tapioca Pearls", 30, 100, Items.Item.ItemType.ADDON_INGREDIENT, "tapioca_pearls_image.png");
            specialVendingMachine.addAddonItem("Taro Balls", 30, 60, Items.Item.ItemType.ADDON_INGREDIENT, "taro_balls_image.png");
            specialVendingMachine.addAddonItem("Sesame Balls", 30, 100, Items.Item.ItemType.ADDON_INGREDIENT, "sesame_balls_image.png");
            specialVendingMachine.addAddonItem("Nata de Coco", 25, 60, Items.Item.ItemType.ADDON_INGREDIENT, "nata_de_coco_image.png");
            specialVendingMachine.addAddonItem("Coffee Jelly", 30, 50, Items.Item.ItemType.ADDON_INGREDIENT, "coffee_jelly_image.png");
            specialVendingMachine.addAddonItem("Fruit Jelly", 30, 50, Items.Item.ItemType.ADDON_INGREDIENT, "fruit_jelly_image.png");
            specialVendingMachine.addAddonItem("Grass Jelly", 40, 40, Items.Item.ItemType.ADDON_INGREDIENT, "grass_jelly_image.png");
            specialVendingMachine.addAddonItem("Oreo Crumbs", 50, 100, Items.Item.ItemType.ADDON_INGREDIENT, "oreo_crumbs_image.png");
            specialVendingMachine.addAddonItem("Egg Pudding", 55, 100, Items.Item.ItemType.ADDON_INGREDIENT, "egg_pudding_image.png");
            specialVendingMachine.addAddonItem("Cream Cheese Foam", 60, 100, Items.Item.ItemType.ADDON_INGREDIENT, "cream_cheese_foam_image.png");
            specialVendingMachine.addAddonItem("Honey", 45, 80, Items.Item.ItemType.ADDON_INGREDIENT, "honey_image.png");
            specialVendingMachine.addAddonItem("Marshmallows", 20, 50, Items.Item.ItemType.ADDON_INGREDIENT, "marshmallows_image.png");
            specialVendingMachine.addAddonItem("Mochi", 40, 80, Items.Item.ItemType.ADDON_INGREDIENT, "mochi_image.png");
            specialVendingMachine.addAddonItem("Whipped Cream", 50, 100, Items.Item.ItemType.ADDON_INGREDIENT, "whipped_cream_image.png");
        }
    }
}
