import entities.Book;
import entities.BookLend;
import entities.Patron;
import services.BookService;
import services.LendService;
import services.PatronService;
import services.RecommendationService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PatronService patronService = new PatronService();
        BookService bookService = new BookService();
        LendService lendService = new LendService(patronService, bookService);
        RecommendationService recommendationService = new RecommendationService(bookService);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        createPatron(scanner, patronService);
                        break;
                    case 2:
                        createBook(scanner, bookService);
                        break;
                    case 3:
                        listPatrons(patronService);
                        break;
                    case 4:
                        listBooks(bookService);
                        break;
                    case 5:
                        lendBook(scanner, lendService);
                        break;
                    case 6:
                        returnBook(scanner, lendService);
                        break;
                    case 7:
                        showActiveLends(lendService);
                        break;
                    case 8:
                        showLendHistory(lendService);
                        break;
                    case 9:
                        searchBooks(scanner, bookService);
                        break;
                    case 10:
                        showRecommendations(scanner, patronService, recommendationService);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting.");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== Library Tracking System ===");
        System.out.println("1. Create patron");
        System.out.println("2. Create book");
        System.out.println("3. List patrons");
        System.out.println("4. List books");
        System.out.println("5. Lend book");
        System.out.println("6. Return book");
        System.out.println("7. Show active lends");
        System.out.println("8. Show lend history");
        System.out.println("9. Search books");
        System.out.println("10. Show recommendations for patron");
        System.out.println("0. Exit");
    }

    private static void createPatron(Scanner scanner, PatronService patronService) {
        String name = readLine(scanner, "Enter patron name: ");
        String email = readLine(scanner, "Enter patron email: ");
        Patron patron = patronService.createPatron(name, email);
        System.out.println("Created: " + patron);
    }

    private static void createBook(Scanner scanner, BookService bookService) {
        String title = readLine(scanner, "Enter book title: ");
        String author = readLine(scanner, "Enter book author: ");
        String isbn = readLine(scanner, "Enter book ISBN: ");
        String genre = readLine(scanner, "Enter book genre: ");
        Book book = bookService.createBook(title, author, isbn, genre);
        System.out.println("Created: " + book);
    }

    private static void listPatrons(PatronService patronService) {
        List<Patron> patrons = patronService.getAllPatrons();
        if (patrons.isEmpty()) {
            System.out.println("No patrons found.");
            return;
        }
        for (Patron patron : patrons) {
            System.out.println(patron);
        }
    }

    private static void listBooks(BookService bookService) {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void lendBook(Scanner scanner, LendService lendService) {
        int patronId = readInt(scanner, "Enter patron ID: ");
        int bookId = readInt(scanner, "Enter book ID: ");
        int lendDays = readInt(scanner, "Enter lend days: ");
        BookLend lend = lendService.lendBook(patronId, bookId, lendDays);
        System.out.println("Lent: " + lend);
    }

    private static void returnBook(Scanner scanner, LendService lendService) {
        int bookId = readInt(scanner, "Enter book ID to return: ");
        BookLend lend = lendService.returnBook(bookId);
        System.out.println("Returned: " + lend);
    }

    private static void showActiveLends(LendService lendService) {
        List<BookLend> lends = lendService.getActiveLends();
        if (lends.isEmpty()) {
            System.out.println("No active lends.");
            return;
        }
        for (BookLend lend : lends) {
            System.out.println(lend);
        }
    }

    private static void showLendHistory(LendService lendService) {
        List<BookLend> lends = lendService.getLendHistory();
        if (lends.isEmpty()) {
            System.out.println("No lend history.");
            return;
        }
        for (BookLend lend : lends) {
            System.out.println(lend);
        }
    }

    private static void searchBooks(Scanner scanner, BookService bookService) {
        System.out.println("Search by:");
        System.out.println("1. ISBN");
        System.out.println("2. Name");
        System.out.println("3. Author");
        System.out.println("4. Genre");
        int option = readInt(scanner, "Choose search option: ");

        switch (option) {
            case 1: {
                String isbn = readLine(scanner, "Enter ISBN: ");
                System.out.println(bookService.getBookByISBN(isbn));
                break;
            }
            case 2: {
                String name = readLine(scanner, "Enter name: ");
                printBooks(bookService.getBooksByName(name));
                break;
            }
            case 3: {
                String author = readLine(scanner, "Enter author: ");
                printBooks(bookService.getBooksByAuthor(author));
                break;
            }
            case 4: {
                String genre = readLine(scanner, "Enter genre: ");
                printBooks(bookService.getBooksByGenre(genre));
                break;
            }
            default:
                System.out.println("Invalid search option.");
        }
    }

    private static void showRecommendations(
            Scanner scanner,
            PatronService patronService,
            RecommendationService recommendationService
    ) {
        int patronId = readInt(scanner, "Enter patron ID: ");
        Patron patron = patronService.getPatronById(patronId);
        List<Book> recommendations = recommendationService.recommendBooks(patron);
        printBooks(recommendations);
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
