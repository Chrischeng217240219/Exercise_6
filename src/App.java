public class App {
    public String storeName;
    public String storeAddress;
    public String storeEmail;
    public long storePhone;
    public String storeMenu;

    private String pizzaIngredients;
    private double pizzaPrice;

    private String sides;
    private String drinks;

    private String orderID;
    private double orderTotal;

    private String blackListedNumber = "12345678901234";

    public App(String name, String address, String email, long phone, String menu) {
        this.storeName = name;
        this.storeAddress = address;
        this.storeEmail = email;
        this.storePhone = phone;
        this.storeMenu = menu;
    }

    public void takeOrder(String id, String ingredients, double price, String sides, String drinks) {
        this.orderID = id;
        this.pizzaIngredients = ingredients;
        this.pizzaPrice = price;
        this.sides = sides;
        this.drinks = drinks;
        this.orderTotal = price + 5.0;

        System.out.println("Order accepted!");
        System.out.println("Order is being prepared...");

        makePizza();
    }

    private void makePizza() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Pizza preparation interrupted!");
        }

        System.out.println("Your pizza is ready!");
        printReceipt();
    }

    private void printReceipt() {
        System.out.println("******** RECEIPT ********");
        System.out.println("Store Name: " + storeName);
        System.out.println("Store Address: " + storeAddress);
        System.out.println("Order ID: " + orderID);
        System.out.println("Pizza Ingredients: " + pizzaIngredients);
        System.out.println("Sides: " + sides);
        System.out.println("Drinks: " + drinks);
        System.out.println("Order Total: $" + orderTotal);
        System.out.println("*************************");
    }

    public void processCardPayment(String cardNumber, String expiryDate, int cvv) {
        int cardLength = cardNumber.length();
        if (cardLength == 14) {
            System.out.println("Card accepted");
        } else {
            System.out.println("Invalid card");
            return;
        }

        int firstCardDigit = Integer.parseInt(cardNumber.substring(0, 1));

        if (cardNumber.equals(blackListedNumber)) {
            System.out.println("Card is blackListed. Please use another card");
            return;
        }

        int lastFourDigits = Integer.parseInt(cardNumber.substring(cardNumber.length() - 4));

        String cardNumberToDisplay = cardNumber.charAt(0) + 
                                     "*".repeat(cardNumber.length() - 5) + 
                                     cardNumber.substring(cardNumber.length() - 4);

        System.out.println("First Digit: " + firstCardDigit);
        System.out.println("Last Four Digits: " + lastFourDigits);
        System.out.println("Card Number to Display: " + cardNumberToDisplay);
    }

    public void specialOfTheDay(String pizzaOfTheDay, String sideOfTheDay, String specialPrice) {
        StringBuilder special = new StringBuilder();
        special.append("Today's Special:\n");
        special.append("Pizza: ").append(pizzaOfTheDay).append("\n");
        special.append("Side: ").append(sideOfTheDay).append("\n");
        special.append("Special Price: ").append(specialPrice).append("\n");

        System.out.println(special.toString());
    }

    public static void main(String[] args) {
        App store = new App("Slice-o-Heaven", "123 Pizza Lane", "contact@sliceoheaven.com", 1234567890, "Pizza, Sides, Drinks");

        store.takeOrder("ORDER123", "Pepperoni, Cheese, Tomato Sauce", 10.99, "Garlic Bread", "Coke");

        store.processCardPayment("12345678901234", "12/25", 123);

        store.specialOfTheDay("Margherita", "Garlic Bread", "$9.99");
    }
}