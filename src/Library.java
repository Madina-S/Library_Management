import java.util.ArrayList;

public class Library {

    ArrayList<Book> books;
    ArrayList<BookCopy> bookCopies;
    ArrayList<Borrower> borrowers;

    public Library(){
        books = new ArrayList<>();
    }

    public void issueBookCopy(int bookCopyID, Borrower borrower){
        for (BookCopy bookCopy : bookCopies)
            if(bookCopy.getBookCopyID() == bookCopyID){
                bookCopy.setBorrower(borrower);
                borrower.addBorrowedBook(bookCopy);
                return;
            }
    }

    public void returnBookCopy(int bookCopyID, int borrowerID){
        for(Borrower borrower : borrowers)
            if(borrower.getID() == borrowerID)
                borrower.removeBorrowedBook(bookCopyID);
    }

    public void addBook(Book book, ArrayList<BookCopy> bookCopies){
        book.setBookCopies(bookCopies);
        bookCopies.addAll(bookCopies);
        books.add(book);
    }
}
