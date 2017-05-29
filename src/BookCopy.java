public class BookCopy {

	private int bookID;
    private int borrowerID = -1;

    public BookCopy(int bookID){
        this.bookID = bookID;
    }

    public BookCopy(int bookID, int borrowerID){
        this(bookID);
        this.borrowerID = borrowerID;
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
