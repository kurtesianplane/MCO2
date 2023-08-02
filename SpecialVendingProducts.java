public class SpecialVendingProducts {
    public static void addDefaultProducts(SpecialVendingMachine specialVendingMachine) {
        specialVendingMachine.addItem("Okinawa", 49, 410);
        specialVendingMachine.addItem("Wintermelon", 59, 435);
        specialVendingMachine.addItem("Hokkaido", 59, 455);
        specialVendingMachine.addItem("Taro", 65, 455);
        specialVendingMachine.addItem("Hazelnut", 79, 475);
        specialVendingMachine.addItem("Salted Caramel", 89, 510);
        specialVendingMachine.addItem("Double Dutch", 99, 510);
        specialVendingMachine.addItem("Black Forest", 105, 565);
        specialVendingMachine.addItem("Choco Java", 119, 585);
        specialVendingMachine.addItem("Matcha Cookie", 119, 605);

        specialVendingMachine.addStandaloneItem("Black Tea", 16, 0);
        specialVendingMachine.addStandaloneItem("Brown Sugar", 37, 2000);
        specialVendingMachine.addStandaloneItem("Milk", 17, 150);
        specialVendingMachine.addStandaloneItem("Water", 0, 0);
        specialVendingMachine.addStandaloneItem("Ice", 16, 0);
        
        specialVendingMachine.addAddonItem("Tapioca Pearls",17 ,135); 
        specialVendingMachine.addAddonItem("Taro Balls",22 ,180); 
        specialVendingMachine.addAddonItem("Sesame Balls",18 ,150); 
        specialVendingMachine.addAddonItem("Nata de Coco",13 ,70); 
        specialVendingMachine.addAddonItem("Coffee Jelly",18 ,50); 
        specialVendingMachine.addAddonItem("Fruit Jelly",15 ,100); 
        specialVendingMachine.addAddonItem("Grass Jelly",15 ,10); 
        specialVendingMachine.addAddonItem("Oreo Crumbs",11 ,53); 
        specialVendingMachine.addAddonItem("Egg Pudding",18 ,100); 
        specialVendingMachine.addAddonItem("Cream Cheese Foam",22 ,100); 
        specialVendingMachine.addAddonItem("Honey",13 ,64); 
        specialVendingMachine.addAddonItem("Marshmallows",11 ,23); 
        specialVendingMachine.addAddonItem("Mochi",18 ,45); 
        specialVendingMachine.addAddonItem("Whipped Cream",15 ,8); 
    }
}
