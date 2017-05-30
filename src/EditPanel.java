import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends JPanel implements ActionListener{

    private int id;
    private String name;
    private String surname;
    private String phoneNumber;

    private Admin admin;

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

    public void set(int id, String name, String surname, String phoneNumber){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        setNameField(name);
        setSurnameField(surname);
        setPhoneNumberField(phoneNumber);
    }

    public void set(){
        set(-1, "", "", "");
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

    private void addToDataBase() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancel){
            setVisible(false);
            return;
        }

        if(e.getSource() == save){
            if(id == -1){
                addToDataBase();
            }
        }
    }
}
