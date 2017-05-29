import java.util.ArrayList;

public class Admin extends User{

    private ArrayList<Borrower> borrowers;
    private ArrayList<Librarian> librarians;

    public Admin(){
        super(-1, "admin", "", "", "");
        borrowers = new ArrayList<>();
        librarians = new ArrayList<>();
    }

    public ArrayList<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(ArrayList<Borrower> borrowers) {
        this.borrowers = borrowers;
    }

    public ArrayList<Librarian> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(ArrayList<Librarian> librarians) {
        this.librarians = librarians;
    }

    public void addBorrower(Borrower borrower){
        borrowers.add(borrower);
    }

    public void removeBorrower(Borrower borrower){
        for(int i = 0; i < borrowers.size(); i++)
            if(borrowers.get(i).getID() == borrower.getID())
                borrowers.remove(i);
    }

    public void addLibrarian(Librarian librarian){
        librarians.add(librarian);
    }

    public void removeLibrarian(Librarian librarian){
        for(int i = 0; i < librarians.size(); i++)
            if(librarians.get(i).getID() == librarian.getID())
                librarians.remove(i);
    }

    public void info(){
        System.out.println("Borrowers");
        for(Borrower b : borrowers)
            System.out.println(b.getName());

        System.out.println("librarians");
        for(Librarian l : librarians)
            System.out.println(l.getName());

        System.out.println("That is all");
    }
}
