public class Book {

	private String bookID;
	private String title;
	private String ISBN;
	private int edition;
	private int quantity;

    public Book(String bookID, String title, String ISBN, int edition, int quantity)
    {
        this.bookID = bookID;
        this.title = title;
        this.ISBN = ISBN;
        this.edition = edition;
        this.quantity = quantity;
    }
}

