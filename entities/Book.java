package entities;

public class Book {
    private final int id;
    private String title;
    private String author;
    private String isbn;
    private boolean lentOut;

    public Book(int id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.lentOut = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isLentOut() {
        return lentOut;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setLentOut(boolean lentOut) {
        this.lentOut = lentOut;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "', isbn='" + isbn + "', lentOut=" + lentOut + "}";
    }
}
