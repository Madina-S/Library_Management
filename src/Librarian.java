import java.util.ArrayList;

public class Librarian extends User {

    private Library library;

    public Librarian(int ID, String password, String name, String surname, String phoneNumber){
        super(ID, password, name, surname, phoneNumber);
        library = new Library();
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
