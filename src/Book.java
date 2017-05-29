import java.util.ArrayList;

public class Book {

    private String bookID;
    private String title;
    private String author;
    private String ISBN;
    private int edition;
    private ArrayList<BookCopy> bookCopies;

    public Book(String bookID, String title, String ISBN, int edition) {
        this.bookID = bookID;
        this.title = title;
        this.ISBN = ISBN;
        this.edition = edition;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public ArrayList<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(ArrayList<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public void addBookCopy(BookCopy bookCopy) {
        bookCopies.add(bookCopy);
    }

    public int getQuantity(){
        return bookCopies.size();
    }
}
