import java.util.ArrayList;

public class Borrower extends User{
	private ArrayList<BookCopy> borrowedBooks;

	public Borrower(int borrowerID, String password, String name, String surname, String phoneNumber)
	{
        super(borrowerID, password, name, surname, phoneNumber);
		borrowedBooks = new ArrayList<>();
	}

	public ArrayList<BookCopy> getBorrowedBooks(){
		return borrowedBooks;
	}

	public void addBorrowedBook(BookCopy bookCopy){
		borrowedBooks.add(bookCopy);
	}

	public void removeBorrowedBook(int bookCopyID){
		for(int i = 0; i < borrowedBooks.size(); i++)
			if(borrowedBooks.get(i).getBookCopyID() == bookCopyID){
				borrowedBooks.get(i).setBorrowerID(-1);
				borrowedBooks.remove(i);
			}
	}
}
