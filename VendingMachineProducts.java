public class VendingMachineProducts {
    public static void addDefaultProducts(VendingMachine vendingMachine) {
        vendingMachine.addItem("Okinawa", 49, 410);
        vendingMachine.addItem("Wintermelon", 59, 435);
        vendingMachine.addItem("Hokkaido", 59, 455);
        vendingMachine.addItem("Taro", 65, 455);
        vendingMachine.addItem("Hazelnut", 79, 475);
        vendingMachine.addItem("Salted Caramel", 89, 510);
        vendingMachine.addItem("Double Dutch", 99, 510);
        vendingMachine.addItem("Black Forest", 105, 565);
        vendingMachine.addItem("Choco Java", 119, 585);
        vendingMachine.addItem("Matcha Cookie", 119, 605);
        if (vendingMachine instanceof SpecialVendingMachine) {
            SpecialVendingMachine specialVendingMachine = (SpecialVendingMachine) vendingMachine;
            specialVendingMachine.addStandaloneItem("Black Tea", 16, 0, Items.Item.ItemType.STANDALONE_INGREDIENT);
            specialVendingMachine.addStandaloneItem("Brown Sugar", 37, 2000, Items.Item.ItemType.STANDALONE_INGREDIENT);
            specialVendingMachine.addStandaloneItem("Milk", 17, 150, Items.Item.ItemType.STANDALONE_INGREDIENT);
            specialVendingMachine.addStandaloneItem("Water", 0, 0, Items.Item.ItemType.STANDALONE_INGREDIENT);
            specialVendingMachine.addStandaloneItem("Ice", 16, 0, Items.Item.ItemType.STANDALONE_INGREDIENT);

            specialVendingMachine.addAddonItem("Tapioca Pearls", 17, 135, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Taro Balls", 22, 180, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Sesame Balls", 18, 150, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Nata de Coco", 13, 70, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Coffee Jelly", 18, 50, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Fruit Jelly", 15, 100, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Grass Jelly", 15, 10, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Oreo Crumbs", 11, 53, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Egg Pudding", 18, 100, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Cream Cheese Foam", 22, 100, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Honey", 13, 64, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Marshmallows", 11, 23, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Mochi", 18, 45, Items.Item.ItemType.ADDON_INGREDIENT);
            specialVendingMachine.addAddonItem("Whipped Cream", 15, 8, Items.Item.ItemType.ADDON_INGREDIENT);
        }
    }
}
