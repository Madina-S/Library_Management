import java.util.ArrayList;

public class Admin extends User{

    ArrayList<Book> books;
    ArrayList<Borrower> borrowers;
    ArrayList<Librarian> librarians;


    public Admin(int ID, String password, String name, String surname, String phoneNumber){
        super(ID, password, name, surname, phoneNumber);
    }
}
