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
        for(int i = 0; i < borrowers.size(); i++)
            if(borrowers.get(i).getID() == borrower.getID())
                borrowers.remove(i);
    }

    public void addLibrarian(User librarian){
        librarians.add(librarian);
    }

    public void removeLibrarian(User librarian){
        for(int i = 0; i < librarians.size(); i++)
            if(librarians.get(i).getID() == librarian.getID())
                librarians.remove(i);
    }
}
