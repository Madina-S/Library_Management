import org.apache.commons.dbutils.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditPanel extends JPanel implements ActionListener{

    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
    private int userType;

    private Admin admin;
    private JTable table;
    private int row = -1;

    private JTextField nameFiled;
    private JTextField surnameFiled;
    private JTextField phoneNumberField;

    private JButton save;
    private JButton cancel;

    public EditPanel(Admin admin){
        this.admin = admin;

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

        JLabel nameL = new JLabel("Name");
        resize(nameL);
        JLabel surnameL = new JLabel("Surname");
        resize(surnameL);
        JLabel phoneNumberL = new JLabel("Phone number");
        resize(phoneNumberL);

        setPreferredSize(new Dimension(300, 100));
        add(Box.createRigidArea(new Dimension(200, 100)), c);
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
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
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
        if(!nameFiled.getText().equals(name))
            return true;

        if(!surnameFiled.getText().equals(surname))
            return true;

        if(!phoneNumberField.getText().equals(phoneNumber))
            return true;

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
        String tableName = "";
        char type = '0';
        if(userType == 0){
            tableName = "borrower";
            type = 'b';
        }else if(userType == 1){
            tableName = "librarian";
            type = 'l';
        }
        name = nameFiled.getText();
        surname = surnameFiled.getText();
        phoneNumber = phoneNumberField.getText();
        String query = String.format("UPDATE `%s` SET name = '%s', surname = '%s', phoneNumber = '%s' " +
                "WHERE borrowerID = %d AND position = '%c';", tableName, name, surname, phoneNumber, id, type);
        return DBManager.edit(query);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancel){
            setVisible(false);
            return;
        }

        if(e.getSource() == save){
            if(id == -1){
                if(addToDataBase()){
                    if(table == null)
                        System.out.println("table is null");
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(new Object[]{id, name, surname, phoneNumber});
                    setVisible(false);
                }
            }else{
                if(!isChanged()){
                    System.out.println("changed");
                    if(editDataBase()){
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setValueAt(name, row, 1);
                        model.setValueAt(surname, row, 2);
                        model.setValueAt(phoneNumber, row, 3);
                        setVisible(false);
                    }
                }
            }
        }
    }
}
