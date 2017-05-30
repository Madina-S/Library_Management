import java.util.ArrayList;

public class Library {

    ArrayList<Book> books;
    ArrayList<BookCopy> bookCopies;
    ArrayList<Borrower> borrowers;

    public Library(){
        books = new ArrayList<>();
        borrowers = new ArrayList<>();
        bookCopies = new ArrayList<>();
    }

    public void issueBookCopy(int bookCopyID, Borrower borrower){
        for (BookCopy bookCopy : bookCopies)
            if(bookCopy.getBookCopyID() == bookCopyID){
                bookCopy.setBorrowerID(borrower.getID());
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

    public void addBook(Book book){
        books.add(book);
    }

    public void addBookCopy(BookCopy bookCopy){
        for(Book book : books)
            if(bookCopy.getBookID() == book.getBookID()){
                book.addBookCopy(bookCopy);
                bookCopies.add(bookCopy);
                break;
            }

        for(Borrower borrower : borrowers)
            if(bookCopy.getBorrowerID() == borrower.getID()){
                borrower.addBorrowedBook(bookCopy);
            }
    }

    public void addBorrower(Borrower borrower){
        borrowers.add(borrower);
    }

    public void info(){
        System.out.println("Books");
        for(Book b : books){
            System.out.print(b.getTitle());
            System.out.println("'s book copies");
            for(BookCopy bc : b.getBookCopies())
                System.out.println(bc.getBookID());
        }

        System.out.println("Book copies");
        for(BookCopy b : bookCopies)
            System.out.println(b.getBookCopyID());

        System.out.println("Borrowers book copy");
        for(Borrower b : borrowers){
            System.out.println(b.getName() + "'s borrowed books");
            for(BookCopy bc : b.getBorrowedBooks())
                System.out.println(bc.getBookCopyID());
        }
    }
}
