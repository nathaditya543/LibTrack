package services;

import entities.Book;
import entities.BookLend;
import entities.Patron;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryService {
    private final Map<Integer, Patron> patronsById = new HashMap<>();
    private final Map<Integer, Book> booksById = new HashMap<>();
    private final Map<Integer, BookLend> activeLendsByBookId = new HashMap<>();
    private final List<BookLend> lendHistory = new ArrayList<>();

    private int patronCounter = 1;
    private int bookCounter = 1;
    private int lendCounter = 1;

    public Patron registerPatron(String name, String email) {
        Patron patron = new Patron(patronCounter++, name, email);
        patronsById.put(patron.getId(), patron);
        return patron;
    }

    public Book addBook(String title, String author, String isbn) {
        Book book = new Book(bookCounter++, title, author, isbn);
        booksById.put(book.getId(), book);
        return book;
    }

    public BookLend lendBook(int patronId, int bookId, int lendDays) {
        Patron patron = patronsById.get(patronId);
        Book book = booksById.get(bookId);

        if (patron == null) {
            throw new IllegalArgumentException("Patron not found: " + patronId);
        }
        if (book == null) {
            throw new IllegalArgumentException("Book not found: " + bookId);
        }
        if (book.isLentOut()) {
            throw new IllegalStateException("Book already lent out: " + bookId);
        }

        LocalDate lendDate = LocalDate.now();
        LocalDate dueDate = lendDate.plusDays(lendDays);

        BookLend lend = new BookLend(lendCounter++, patron, book, lendDate, dueDate);
        book.setLentOut(true);
        activeLendsByBookId.put(bookId, lend);
        lendHistory.add(lend);

        return lend;
    }

    public BookLend returnBook(int bookId) {
        BookLend lend = activeLendsByBookId.remove(bookId);
        if (lend == null) {
            throw new IllegalStateException("No active lend found for book: " + bookId);
        }

        lend.markReturned(LocalDate.now());
        lend.getBook().setLentOut(false);
        return lend;
    }

    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patronsById.values());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(booksById.values());
    }

    public List<BookLend> getActiveLends() {
        return new ArrayList<>(activeLendsByBookId.values());
    }

    public List<BookLend> getLendHistory() {
        return new ArrayList<>(lendHistory);
    }
}
