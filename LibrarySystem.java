import java.io.*;
import java.util.*;

class Book implements Comparable<Book> {
    // Book class stores book details and issue status
    int bookId;
    String title;
    String author;
    String category;
    boolean isIssued;

    // constructor
    public Book(int bookId, String title, String author, String category, boolean isIssued) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = isIssued;
    }

    // displays book details
    public void displayBookDetails() {
        System.out.println("ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Category: " + category + ", Issued: " + isIssued);
    }

    // marks book as issued
    public void markAsIssued() {
        this.isIssued = true;
    }

    // marks book as returned
    public void markAsReturned() {
        this.isIssued = false;
    }

    // compare books by title
    public int compareTo(Book b) {
        return this.title.compareToIgnoreCase(b.title);
    }
}

class Member {
    // Member class stores member information and issued book IDs
    int memberId;
    String name;
    String email;
    List<Integer> issuedBooks;

    // constructor
    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.issuedBooks = new ArrayList<>();
    }

    // displays member details
    public void displayMemberDetails() {
        System.out.println("Member ID: " + memberId + ", Name: " + name + ", Email: " + email + ", Issued Books: " + issuedBooks);
    }

    // adds a book to issued list
    public void addIssuedBook(int bookId) {
        issuedBooks.add(bookId);
    }

    // removes a book from issued list
    public void returnIssuedBook(int bookId) {
        issuedBooks.remove(Integer.valueOf(bookId));
    }
}

class LibraryManager {
    // LibraryManager stores books and members and handles operations
    Map<Integer, Book> books = new HashMap<>();
    Map<Integer, Member> members = new HashMap<>();

    String booksFile = "books.txt";
    String membersFile = "members.txt";

    // loads data from files
    public void loadFromFile() {
        loadBooks();
        loadMembers();
    }

    // saves data to files
    public void saveToFile() {
        saveBooks();
        saveMembers();
    }

    // adds a new book
    public void addBook(Scanner sc) {
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        int id = books.size() + 101;
        Book b = new Book(id, title, author, category, false);
        books.put(id, b);
        saveBooks();

        System.out.println("Book added with ID: " + id);
    }

    // adds a new member
    public void addMember(Scanner sc) {
        System.out.print("Enter Member Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        int id = members.size() + 1;
        Member m = new Member(id, name, email);
        members.put(id, m);
        saveMembers();

        System.out.println("Member added with ID: " + id);
    }

    // issues a book to a member
    public void issueBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int bookId = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Member ID: ");
        int memberId = Integer.parseInt(sc.nextLine());

        if (!books.containsKey(bookId)) {
            System.out.println("Book not found");
            return;
        }
        if (!members.containsKey(memberId)) {
            System.out.println("Member not found");
            return;
        }
        Book b = books.get(bookId);
        if (b.isIssued) {
            System.out.println("Book already issued");
            return;
        }

        b.markAsIssued();
        members.get(memberId).addIssuedBook(bookId);

        saveBooks();
        saveMembers();

        System.out.println("Book issued successfully");
    }

    // returns a book
    public void returnBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int bookId = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Member ID: ");
        int memberId = Integer.parseInt(sc.nextLine());

        if (!books.containsKey(bookId) || !members.containsKey(memberId)) {
            System.out.println("Invalid IDs");
            return;
        }

        Book b = books.get(bookId);
        Member m = members.get(memberId);

        if (!b.isIssued) {
            System.out.println("Book is not issued");
            return;
        }

        b.markAsReturned();
        m.returnIssuedBook(bookId);

        saveBooks();
        saveMembers();

        System.out.println("Book returned successfully");
    }

    // searches books by title, author, or category
    public void searchBooks(Scanner sc) {
        System.out.print("Search keyword: ");
        String key = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(key) ||
                b.author.toLowerCase().contains(key) ||
                b.category.toLowerCase().contains(key)) {
                b.displayBookDetails();
            }
        }
    }

    // sorts books by title, author, or category
    public void sortBooks(Scanner sc) {
        List<Book> list = new ArrayList<>(books.values());

        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");
        System.out.println("3. Sort by Category");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice == 1) {
            Collections.sort(list);
        } else if (choice == 2) {
            Collections.sort(list, new Comparator<Book>() {
                public int compare(Book a, Book b) {
                    return a.author.compareToIgnoreCase(b.author);
                }
            });
        } else if (choice == 3) {
            Collections.sort(list, new Comparator<Book>() {
                public int compare(Book a, Book b) {
                    return a.category.compareToIgnoreCase(b.category);
                }
            });
        }

        for (Book b : list) b.displayBookDetails();
    }

    // loads books from file
    private void loadBooks() {
        try {
            File f = new File(booksFile);
            if (!f.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int id = Integer.parseInt(p[0]);
                String title = p[1];
                String author = p[2];
                String category = p[3];
                boolean issued = Boolean.parseBoolean(p[4]);

                books.put(id, new Book(id, title, author, category, issued));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading books");
        }
    }

    // loads members from file
    private void loadMembers() {
        try {
            File f = new File(membersFile);
            if (!f.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int id = Integer.parseInt(p[0]);
                String name = p[1];
                String email = p[2];

                Member m = new Member(id, name, email);
                if (p.length > 3) {
                    String[] bookIds = p[3].split(";");
                    for (String b : bookIds) {
                        if (!b.isEmpty()) m.addIssuedBook(Integer.parseInt(b));
                    }
                }
                members.put(id, m);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading members");
        }
    }

    // saves book records to file
    private void saveBooks() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile));

            for (Book b : books.values()) {
                bw.write(b.bookId + "," + b.title + "," + b.author + "," + b.category + "," + b.isIssued);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving books");
        }
    }

    // saves member records to file
    private void saveMembers() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(membersFile));

            for (Member m : members.values()) {
                StringBuilder sb = new StringBuilder();
                for (int id : m.issuedBooks) sb.append(id).append(";");

                bw.write(m.memberId + "," + m.name + "," + m.email + "," + sb.toString());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving members");
        }
    }
}

public class LibrarySystem {
    // main method runs the menu loop
    public static void main(String[] args) {
        LibraryManager lm = new LibraryManager();
        lm.loadFromFile();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) lm.addBook(sc);
            else if (ch == 2) lm.addMember(sc);
            else if (ch == 3) lm.issueBook(sc);
            else if (ch == 4) lm.returnBook(sc);
            else if (ch == 5) lm.searchBooks(sc);
            else if (ch == 6) lm.sortBooks(sc);
            else if (ch == 7) {
                lm.saveToFile();
                break;
            }
        }
        sc.close();
    }
}
