import java.util.*;

// Custom exception for invalid marks
class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

// Represents a student and their marks
class Student {

    private int rollNumber;
    private String studentName;
    private int[] marks = new int[3];

    // Constructor to set student details
    public Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    // Validates marks to be between 0 and 100
    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i + 1) + ": " + marks[i]);
            }
        }
    }

    // Calculates and returns the average marks
    public double calculateAverage() {
        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        return sum / 3.0;
    }

    // Displays student result and pass/fail status
    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);
        System.out.println("Marks: " + marks[0] + " " + marks[1] + " " + marks[2]);
        double avg = calculateAverage();
        System.out.println("Average: " + avg);

        if (avg >= 33) {
            System.out.println("Result: Pass");
        } else {
            System.out.println("Result: Fail");
        }
    }

    public int getRollNumber() {
        return rollNumber;
    }
}

// Manages students and operations with exception handling
public class StudentResultManager {

    private Student[] students = new Student[100];
    private int count = 0;
    private Scanner sc = new Scanner(System.in);

    // Adds a new student with validation logic
    public void addStudent() throws InvalidMarksException {
        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
        }

        Student s = new Student(roll, name, marks);
        s.validateMarks();

        students[count++] = s;
        System.out.println("Student added successfully.");
    }

    // Displays details of a student using roll number
    public void showStudentDetails() {
        System.out.print("Enter Roll Number to search: ");
        int roll = sc.nextInt();

        for (int i = 0; i < count; i++) {
            if (students[i].getRollNumber() == roll) {
                students[i].displayResult();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Menu for user operations
    public void mainMenu() {
        while (true) {
            try {
                System.out.println("\n===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();

                if (choice == 1) {
                    try {
                        addStudent();
                    } catch (InvalidMarksException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice == 2) {
                    showStudentDetails();
                } else if (choice == 3) {
                    System.out.println("Exiting program. Thank you!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Input mismatch. Please enter valid input.");
                sc.nextLine();
            }
        }
    }

    // Main method that ensures scanner is closed in a finally block
    public static void main(String[] args) {
        StudentResultManager rm = new StudentResultManager();
        try {
            rm.mainMenu();
        } finally {
            rm.sc.close();
            System.out.println("Scanner closed.");
        }
    }
}
