import java.util.ArrayList;

public class Book {

    private int bookID;
    private String title;
    private String author;
    private String ISBN;
    private int edition;
    private ArrayList<BookCopy> bookCopies;

    public Book(int bookID){
        this.bookID = bookID;
        bookCopies = new ArrayList<>();
    }

    public Book(int bookID, String title, String author, String ISBN, int edition) {
        this(bookID);
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.edition = edition;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
