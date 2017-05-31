import org.apache.commons.dbutils.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public class EditPanel extends JPanel implements ActionListener, KeyListener{

    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
    private int userType;

    private Admin admin;
    private JTable table;
    private AdminFrame frame;
    private int row = -1;

    private JTextField nameFiled;
    private JTextField surnameFiled;
    private JTextField phoneNumberField;

    private JLabel error;

    private JButton save;
    private JButton cancel;

    public EditPanel(Admin admin, AdminFrame frame){
        this.admin = admin;
        this.frame = frame;

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;

        nameFiled = new JTextField("", 15);
        surnameFiled = new JTextField("", 15);
        phoneNumberField = new JTextField("", 15);
        resize(nameFiled);
        resize(surnameFiled);
        resize(phoneNumberField);
        nameFiled.addKeyListener(this);
        surnameFiled.addKeyListener(this);
        phoneNumberField.addKeyListener(this);

        error = new JLabel("");
        error.setForeground(Color.RED);
        resize(error);

        JLabel nameL = new JLabel("Name");
        resize(nameL);
        JLabel surnameL = new JLabel("Surname");
        resize(surnameL);
        JLabel phoneNumberL = new JLabel("Phone number");
        resize(phoneNumberL);

        setPreferredSize(new Dimension(300, 100));
        add(Box.createRigidArea(new Dimension(200, 100)), c);
        add(error, c);
        add(Box.createRigidArea(new Dimension(5, 5)), c);
        add(nameL, c);
        add(nameFiled, c);
        add(surnameL, c);
        add(surnameFiled, c);
        add(phoneNumberL, c);
        add(phoneNumberField, c);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        save = new JButton("Save");
        save.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        panel.add(save);
        panel.add(cancel);
        add(Box.createRigidArea(new Dimension(30, 30)), c);
        add(panel, c);
        add(Box.createRigidArea(new Dimension(200, 150)), c);

        setVisible(false);
    }

    private void resize(JComponent component){
        component.setPreferredSize(new Dimension(150, 30));
        component.setMaximumSize(new Dimension(150, 30));
    }

    public void set(int id, String name, String surname, String phoneNumber, int userType, JTable table, int row){
        this.id = id;
        this.row = row;
        this.name = name.trim();
        this.surname = surname.trim();
        this.phoneNumber = phoneNumber.trim();
        this.userType = userType;
        this.table = table;
        setNameField(name);
        setSurnameField(surname);
        setPhoneNumberField(phoneNumber);
    }

    public void set(int userType, JTable table){
        set(-1, "", "", "", userType, table, -1);
    }

    private void setNameField(String name){
        this.nameFiled.setText(name);
    }

    private void setSurnameField(String surname){
        this.surnameFiled.setText(surname);
    }

    private void setPhoneNumberField(String phoneNumber){
        this.phoneNumberField.setText(phoneNumber);
    }

    private boolean isChanged(){
        if(!nameFiled.getText().trim().equals(name)){
            return true;
        }

        if(!surnameFiled.getText().trim().equals(surname)){
            return true;
        }

        if(!phoneNumberField.getText().trim().equals(phoneNumber)){
            return true;
        }

        return false;
    }

    private boolean addToDataBase() {
        char type = '0';
        String tableName = "";
        if(userType == 0){
            tableName = "borrower";
            type = 'b';
        } else if(userType == 1){
            tableName = "librarian";
            type = 'l';
        }
        String sql = String.format("INSERT INTO `%s` VALUES (NULL);", tableName);
        Result result = DBManager.insertWithID(sql);
        if(result == null){
            System.out.println("result is null");
            return false;
        }

        name = nameFiled.getText();
        surname = surnameFiled.getText();
        phoneNumber = phoneNumberField.getText();
        boolean isAdded = false;
        try {
            id = result.resultSet.getInt(1);
            sql = String.format("INSERT INTO `user` (id, password, name, surname, phoneNumber, position) " +
                    "VALUES (%d, '123', '%s', '%s', '%s', '%c')", id, name, surname, phoneNumber, type);
            isAdded = DBManager.insert(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(result.resultSet);
            DbUtils.closeQuietly(result.statement);
            return isAdded;
        }
    }

    private boolean editDataBase(){
        char type = '0';
        if(userType == 0){
            type = 'b';
        }else if(userType == 1){
            type = 'l';
        }
        name = nameFiled.getText();
        surname = surnameFiled.getText();
        phoneNumber = phoneNumberField.getText();
        String query = String.format("UPDATE `user` SET name = '%s', surname = '%s', phoneNumber = '%s' " +
                "WHERE id = %d AND position = '%c';", name, surname, phoneNumber, id, type);
        return DBManager.edit(query);
    }

    private boolean isDataValid(){
        if(nameFiled.getText().trim().isEmpty())
            return false;

        if(surnameFiled.getText().trim().isEmpty())
            return false;

        if(phoneNumberField.getText().trim().isEmpty())
            return false;

        return true;
    }

    @Override
    public void setVisible(boolean aFlag){
        super.setVisible(aFlag);
        frame.enableButtons(!aFlag);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancel){
            setVisible(false);
            return;
        }

        if(e.getSource() == save){
            if(!isDataValid()){
                error.setText("Not valid info");
                return;
            }

            if(id == -1){
                if(addToDataBase()){
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(new Object[]{id, name, surname, phoneNumber});
                    setVisible(false);
                }
            }else{
                if(isChanged()){
                    if(editDataBase()){
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setValueAt(name, row, 1);
                        model.setValueAt(surname, row, 2);
                        model.setValueAt(phoneNumber, row, 3);
                        setVisible(false);
                    }
                }else{
                    error.setText("There is no change");
                }
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
