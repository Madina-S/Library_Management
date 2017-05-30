import org.apache.commons.dbutils.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public class LoginWindow extends JFrame implements ActionListener, KeyListener{

    private JTextField login;
    private JPasswordField password;
    private JLabel error;

    public LoginWindow(){
        JLabel nameLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        JLabel loginL = new JLabel("Login");
        login = new JTextField("", 15);
        JLabel passwordL = new JLabel("Password");
        password = new JPasswordField();
        JButton submitBtn = new JButton("Login");
        submitBtn.addActionListener(this);
        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setHorizontalAlignment(JLabel.CENTER);

        JPanel mainPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(nameLabel, gbc);

        JPanel compPanel = new JPanel();
        compPanel.setLayout(new GridLayout(2, 2, 5, 3));
        loginL.setHorizontalAlignment(JLabel.RIGHT);
        compPanel.add(loginL);
        compPanel.add(login);
        compPanel.add(passwordL);
        passwordL.setHorizontalAlignment(JLabel.RIGHT);
        compPanel.add(password);

        mainPanel.add(compPanel, gbc);
        gbc.insets = new Insets(10, 90, 10, 90);
        mainPanel.add(submitBtn, gbc);
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(error, gbc);
        this.add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width - this.getSize().width) / 2, (dim.height - this.getSize().height) / 2);

        login.addKeyListener(this);
        password.addKeyListener(this);
    }

    private void getAllUsers(Admin admin, String tableName, char position){
        String query = String .format("SELECT * FROM `user` " +
                "INNER JOIN `%s` " +
                "ON user.id = %s.id AND user.position='%c';", tableName, tableName, position);
        Result result = DBManager.getData(query);
        try {
            while(result.resultSet.next()){
                int id = result.resultSet.getInt("id");
                String password = result.resultSet.getString("password");
                String name = result.resultSet.getString("name");
                String surname = result.resultSet.getString("surname");
                String phoneNumber = result.resultSet.getString("phoneNumber");
                if(position == 'l'){
                    Librarian librarian = new Librarian(id, password, name, surname, phoneNumber);
                    admin.addLibrarian(librarian);
                }else if(position == 'b'){
                    Borrower borrower = new Borrower(id, password, name, surname, phoneNumber);
                    admin.addBorrower(borrower);
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        DbUtils.closeQuietly(result.resultSet);
        DbUtils.closeQuietly(result.statement);
    }

    private void getAllBorrowers(Library library){
        String query = String .format("SELECT * FROM `user` " +
                "INNER JOIN `borrower` " +
                "ON user.id = borrower.id AND user.position='b';");
        Result result = DBManager.getData(query);
        try {
            while(result.resultSet.next()){
                int id = result.resultSet.getInt("id");
                String password = result.resultSet.getString("password");
                String name = result.resultSet.getString("name");
                String surname = result.resultSet.getString("surname");
                String phoneNumber = result.resultSet.getString("phoneNumber");
                Borrower borrower = new Borrower(id, password, name, surname, phoneNumber);
                library.addBorrower(borrower);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        DbUtils.closeQuietly(result.resultSet);
        DbUtils.closeQuietly(result.statement);
    }

    private void getAllBooks(Library library){
        String query = "SELECT * FROM `book`;";
        Result result = DBManager.getData(query);
        try {
            while(result.resultSet.next()){
                int ID = result.resultSet.getInt("id");
                String title = result.resultSet.getString("title");
                String author = result.resultSet.getString("author");
                String ISBN = result.resultSet.getString("ISBN");
                int edition = result.resultSet.getInt("edition");
                Book book = new Book(ID, title, author, ISBN, edition);
                library.addBook(book);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            DbUtils.closeQuietly(result.resultSet);
            DbUtils.closeQuietly(result.statement);
        }
    }

    private void getAllBookCopies(Library library){
        String query = "SELECT * FROM `bookCopy`;";
        Result result = DBManager.getData(query);
        try {
            while(result.resultSet.next()){
                int b_id = result.resultSet.getInt("id");
                int bookID = result.resultSet.getInt("bookID");
                int borrowerID = result.resultSet.getInt("borrowerID");
                BookCopy bookCopy = new BookCopy(b_id, bookID, borrowerID);
                library.addBookCopy(bookCopy);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pass = String.valueOf(password.getPassword());
        if(login.getText().equals("admin")){
            if(pass.equals("admin")) {
                Admin admin = new Admin();

                getAllUsers(admin, "librarian", 'l');
                getAllUsers(admin, "borrower", 'b');

                admin.info();
                ////OPEN ADMIN WINDOW
                return;
            }else{
                error.setText("Wrong login or password");
                return;
            }
        }

        if(login.getText().startsWith("l")){
            int id;
            try{
                id = Integer.parseInt(login.getText().substring(1));
                User user = DBManager.isExist(id);
                if(user == null){
                    error.setText("Wrong login or password");
                    return;
                }
                Librarian librarian = new Librarian(user);
                if(!pass.equals(librarian.getPassword())){
                    error.setText("Wrong login or password");
                    return;
                }

                Library library = new Library();
                getAllBorrowers(library);
                getAllBooks(library);
                getAllBookCopies(library);
                library.info();
                librarian.setLibrary(library);
                //OPEN LIBRARIAN WINDOW
            }catch (IllegalArgumentException ex){
                error.setText("Wrong login or password");
                return;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        error.setText("");
    }
}
