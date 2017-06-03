import org.apache.commons.dbutils.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public abstract class EditPanel extends JPanel implements ActionListener, KeyListener{

    protected int id;
    protected JTable table;
    protected UserFrame frame;
    protected int row = -1;
    protected JLabel error;
    protected JButton save;
    protected JButton cancel;
    protected GridBagConstraints c;

    public EditPanel(UserFrame frame){
        this.frame = frame;

        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;

        error = new JLabel("");
        error.setForeground(Color.RED);
        resize(error);


        setPreferredSize(new Dimension(300, 100));
        add(Box.createRigidArea(new Dimension(200, 100)), c);
        add(error, c);
        add(Box.createRigidArea(new Dimension(5, 5)), c);

        setVisible(false);
    }

    protected void addButtons(){
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
    }

    protected void resize(JComponent component){
        component.setPreferredSize(new Dimension(150, 30));
        component.setMaximumSize(new Dimension(150, 30));
    }

    protected abstract boolean isChanged();

    protected abstract boolean addToDataBase();

    protected abstract boolean editDataBase();

    protected abstract boolean isDataValid();

    @Override
    public void setVisible(boolean aFlag){
        super.setVisible(aFlag);
        frame.enableButtons(!aFlag);
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
