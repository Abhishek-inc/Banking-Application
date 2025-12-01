import java.util.Scanner;

class Calculator {

    // Add two integers
    int add(int num1, int num2) {
        return num1 + num2;
    }

    // Add two double numbers
    double add(double num1, double num2) {
        return num1 + num2;
    }

    // Add three integers
    int add(int num1, int num2, int num3) {
        return num1 + num2 + num3;
    }

    // Subtract second integer from first
    int subtract(int num1, int num2) {
        return num1 - num2;
    }

    // Multiply two double numbers
    double multiply(double num1, double num2) {
        return num1 * num2;
    }

    // Divide two integers, throws exception if denominator is zero
    double divide(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Cannot divide by zero!");
        }
        return (double) numerator / denominator;
    }
}

class UserInterface {

    private final Scanner scanner = new Scanner(System.in);
    private final Calculator calculator = new Calculator();

    // Handles addition operations
    void performAddition() {
        System.out.println("\nChoose addition type:");
        System.out.println("1. Add two integers");
        System.out.println("2. Add two doubles");
        System.out.println("3. Add three integers");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter two integers: ");
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                System.out.println("Result: " + calculator.add(a, b));
            }
            case 2 -> {
                System.out.print("Enter two doubles: ");
                double a = scanner.nextDouble();
                double b = scanner.nextDouble();
                System.out.println("Result: " + calculator.add(a, b));
            }
            case 3 -> {
                System.out.print("Enter three integers: ");
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                int c = scanner.nextInt();
                System.out.println("Result: " + calculator.add(a, b, c));
            }
            default -> System.out.println("Invalid choice! Please try again.");
        }
    }

    // Handles subtraction
    void performSubtraction() {
        System.out.print("\nEnter two integers: ");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        System.out.println("Result: " + calculator.subtract(a, b));
    }

    // Handles multiplication
    void performMultiplication() {
        System.out.print("\nEnter two doubles: ");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        System.out.println("Result: " + calculator.multiply(a, b));
    }

    // Handles division with exception handling
    void performDivision() {
        System.out.print("\nEnter numerator and denominator: ");
        int numerator = scanner.nextInt();
        int denominator = scanner.nextInt();

        try {
            System.out.println("Result: " + calculator.divide(numerator, denominator));
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Main menu to navigate the calculator
    void mainMenu() {
        int choice;

        do {
            System.out.println("\n===== Calculator Application =====");
            System.out.println("1. Add Numbers");
            System.out.println("2. Subtract Numbers");
            System.out.println("3. Multiply Numbers");
            System.out.println("4. Divide Numbers");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> performAddition();
                case 2 -> performSubtraction();
                case 3 -> performMultiplication();
                case 4 -> performDivision();
                case 5 -> System.out.println("Thank you for using the Calculator. Goodbye!");
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
    }
}

public class CalculatorApp {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.mainMenu();
    }
}
