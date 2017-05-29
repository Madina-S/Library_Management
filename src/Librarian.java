import java.util.ArrayList;

public class Librarian extends User {

    ArrayList<Book> books;

    public Librarian(int ID, String password, String name, String surname, String phoneNumber){
        super(ID, password, name, surname, phoneNumber);
    }
}
