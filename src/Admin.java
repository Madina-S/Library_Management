import java.util.ArrayList;

public class Admin extends User{

    private ArrayList<User> borrowers;
    private ArrayList<User> librarians;

    public Admin(){
        super(-1, "admin", "", "", "");
        borrowers = new ArrayList<>();
        librarians = new ArrayList<>();
    }

    public ArrayList<User> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(ArrayList<User> borrowers) {
        this.borrowers = borrowers;
    }

    public ArrayList<User> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(ArrayList<User> librarians) {
        this.librarians = librarians;
    }

    public void addBorrower(User borrower){
        borrowers.add(borrower);
    }

    public void removeBorrower(User borrower){
        removeBorrower(borrower.getID());
    }

    public void addLibrarian(User librarian){
        librarians.add(librarian);
    }

    public void removeLibrarian(User librarian){
        removeLibrarian(librarian.getID());
    }

    public void removeLibrarian(int id){
        for(int i = 0; i < librarians.size(); i++)
            if(librarians.get(i).getID() == id)
                librarians.remove(i);
    }

    public void removeBorrower(int id){
        for(int i = 0; i < borrowers.size(); i++)
            if(borrowers.get(i).getID() == id)
                borrowers.remove(i);
    }
}
