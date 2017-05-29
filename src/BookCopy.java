public class BookCopy {

	private int bookCopyID;
    private Borrower borrower = null;
    private Book book;

    public BookCopy(int bookCopyID, Book book){
        this.bookCopyID = bookCopyID;
        this.book = book;
    }

    public BookCopy(int bookCopyID, Book book, Borrower borrower){
        this(bookCopyID, book);
        this.borrower = borrower;
    }

    public int getBookCopyID() {
        return bookCopyID;
    }

    public void setBookCopyID(int bookCopyID) {
        this.bookCopyID = bookCopyID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }
}
