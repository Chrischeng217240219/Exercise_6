import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    private String storeName;
    private String storeAddress;
    private String pizzaIngredients;
    private String pizzaSize;
    private boolean extraCheese;
    private String sideDish;
    private String drinks;
    private double orderTotal;
    private final long blacklistedNumber = 12345678901234L;

    public App(String name, String address) {
        this.storeName = name;
        this.storeAddress = address;
    }

    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter three ingredients for your pizza (use spaces to separate ingredients):");
        String img1 = scanner.next();
        String img2 = scanner.next();
        String img3 = scanner.next();
        pizzaIngredients = String.join(", ", img1, img2, img3);

        System.out.println("Enter size of pizza (Small, Medium, Large):");
        pizzaSize = scanner.next();

        System.out.println("Do you want extra cheese (Y/N):");
        extraCheese = scanner.next().equalsIgnoreCase("Y");

        System.out.println("Enter one side dish (Calzone, Garlic bread, None):");
        sideDish = scanner.next();

        System.out.println("Enter drinks (Cold Coffee, Cocoa drink, Coke, None):");
        drinks = scanner.next();

        System.out.println("Would you like the chance to pay only half for your order? (Y/N):");
        if (scanner.next().equalsIgnoreCase("Y")) {
            isItYourBirthday();
        } else {
            makeCardPayment();
        }

        calculateTotal();
        printReceipt();
    }

    private void calculateTotal() {
        orderTotal = switch (pizzaSize) {
            case "Small" -> 8.0;
            case "Medium" -> 12.0;
            case "Large" -> 16.0;
            default -> 0;
        };
        if (extraCheese) orderTotal += 2;
        if (!sideDish.equals("None")) orderTotal += 5;
        if (!drinks.equals("None")) orderTotal += 3;
    }

    private void isItYourBirthday() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your birthday (YYYY-MM-DD):");
            LocalDate birthdate = LocalDate.parse(scanner.next(), DateTimeFormatter.ISO_DATE);
            LocalDate today = LocalDate.now();

            boolean isBirthdayToday = birthdate.getMonth() == today.getMonth() 
                                    && birthdate.getDayOfMonth() == today.getDayOfMonth();
            int age = Period.between(birthdate, today).getYears();

            if (age < 18 && isBirthdayToday) {
                orderTotal *= 0.5;
                System.out.println("Congratulations! You pay only half the price for your order");
            } else {
                System.out.println("Too bad! You do not meet the conditions to get our 50% discount");
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Discount not applied.");
        }
    }

    private void makeCardPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        long cardNumber = scanner.nextLong();
        
        System.out.println("Enter card expiry date (YYYY-MM):");
        String expiryDate = scanner.next();
        
        System.out.println("Enter card CVV:");
        int cvv = scanner.nextInt();
        
        processCardPayment(cardNumber, expiryDate, cvv);
    }

    private void processCardPayment(long cardNumber, String expiryDate, int cvv) {
        String cardStr = Long.toString(cardNumber);
        if (cardStr.length() != 14) {
            System.out.println("Invalid card");
            return;
        }

        if (cardNumber == blacklistedNumber) {
            System.out.println("Card is blacklisted. Please use another card");
            return;
        }

        char firstDigit = cardStr.charAt(0);
        String lastFourDigits = cardStr.substring(cardStr.length() - 4);
        String maskedNumber = firstDigit + "********" + lastFourDigits;
        
        System.out.println("Card accepted");
        System.out.println("Masked card number: " + maskedNumber);
    }

    private void printReceipt() {
        System.out.printf("\n******** RECEIPT ********\n" +
                          "Store: %s\nAddress: %s\nIngredients: %s\nSize: %s\n" +
                          "Extra Cheese: %s\nSide: %s\nDrink: %s\nTotal: $%.2f\n" +
                          "*************************\n",
                          storeName, storeAddress, pizzaIngredients, pizzaSize,
                          extraCheese ? "Yes" : "No", sideDish, drinks, orderTotal);
    }

    public static void main(String[] args) {
        App store = new App("Slice-o-Heaven", "123 Pizza Lane");
        store.takeOrder();
    }
}