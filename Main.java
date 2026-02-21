import entities.Book;
import entities.BookLend;
import entities.Patron;
import services.BookService;
import services.LendService;
import services.PatronService;
import services.RecommendationService;

public class Main {
    public static void main(String[] args) {
        PatronService patronService = new PatronService();
        BookService bookService = new BookService();
        LendService lendService = new LendService(patronService, bookService);
        RecommendationService recommendationService = new RecommendationService(bookService);

        Patron patron = patronService.createPatron("Alice Johnson", "alice@example.com");
        Book book = bookService.createBook("Clean Code", "Robert C. Martin", "9780132350884", "Software");
        Book book2 = bookService.createBook("Refactoring", "Martin Fowler", "9780201485677", "Software");
        bookService.createBook("Dune", "Frank Herbert", "9780441172719", "Sci-Fi");

        BookLend lend = lendService.lendBook(patron.getId(), book.getId(), 14);
        System.out.println("Lent: " + lend);

        BookLend returned = lendService.returnBook(book.getId());
        System.out.println("Returned: " + returned);

        System.out.println("Patrons:");
        for (Patron p : patronService.getAllPatrons()) {
            System.out.println(p);
        }

        System.out.println("Books:");
        for (Book b : bookService.getAllBooks()) {
            System.out.println(b);
        }

        System.out.println("Lend history:");
        for (BookLend l : lendService.getLendHistory()) {
            System.out.println(l);
        }

        System.out.println("Recommendations:");
        for (Book recommended : recommendationService.recommendBooks(patron)) {
            System.out.println(recommended);
        }
    }
}
