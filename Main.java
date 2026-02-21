import entities.Book;
import entities.BookLend;
import entities.Patron;
import services.LibraryService;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();

        Patron patron = libraryService.registerPatron("Alice Johnson", "alice@example.com");
        Book book = libraryService.addBook("Clean Code", "Robert C. Martin", "9780132350884");

        BookLend lend = libraryService.lendBook(patron.getId(), book.getId(), 14);
        System.out.println("Lent: " + lend);

        BookLend returned = libraryService.returnBook(book.getId());
        System.out.println("Returned: " + returned);

        System.out.println("Patrons:");
        for (Patron p : libraryService.getAllPatrons()) {
            System.out.println(p);
        }

        System.out.println("Books:");
        for (Book b : libraryService.getAllBooks()) {
            System.out.println(b);
        }

        System.out.println("Lend history:");
        for (BookLend l : libraryService.getLendHistory()) {
            System.out.println(l);
        }
    }
}
