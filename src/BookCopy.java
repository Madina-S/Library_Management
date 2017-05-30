public class BookCopy {

	private int bookCopyID;
    private int bookID;
    private int borrowerID;

    public BookCopy(int bookCopyID, int bookID, int borrowerID){
        this.bookCopyID = bookCopyID;
        this.bookID = bookID;
        this.borrowerID = borrowerID;
    }

    public BookCopy(int bookCopyID){
        this.bookCopyID = bookCopyID;
    }

    public int getBookCopyID() {
        return bookCopyID;
    }

    public void setBookCopyID(int bookCopyID) {
        this.bookCopyID = bookCopyID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }
}
