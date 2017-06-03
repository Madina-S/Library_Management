import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminFrame extends UserFrame implements ActionListener{

    private static String[] column = {"ID", "Name", "Surname", "Phone Number"};
    private Admin admin;
    private AdminEditPanel editPanel;

    public AdminFrame(Admin admin) {
        super();

        this.admin = admin;

        DefaultTableModel dtm = addTable("Borrowers");
        addObjectToTable(dtm, admin.getBorrowers());
        dtm = addTable("Librarians");
        addObjectToTable(dtm, admin.getLibrarians());

        editPanel = new AdminEditPanel(admin, this);
        add(editPanel, BorderLayout.LINE_START);
    }

    private DefaultTableModel addTable(String name){
        return addTable(name, column);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JScrollPane sc = (JScrollPane) tabbedPane.getSelectedComponent();
        int row;
        if (sc != null) {
            JViewport view = sc.getViewport();
            JTable t = (JTable) view.getComponent(0);
            int userType = tabbedPane.getSelectedIndex();
            if(e.getSource() == add){
                editPanel.set(userType, t);
                editPanel.setVisible(true);
                enableButtons(false);
                return;
            }

            row = t.getSelectedRow();
            if(row == -1)
                return;
            t.clearSelection();

            int id = Integer.parseInt(t.getModel().getValueAt(row, 0).toString());
            if(e.getSource() == edit){
                String name = t.getModel().getValueAt(row, 1).toString();
                String surname = t.getModel().getValueAt(row, 2).toString();
                String phoneNumber = t.getModel().getValueAt(row, 3).toString();
                editPanel.set(id, name, surname, phoneNumber, userType, t, row);
                enableButtons(false);
            }else if(e.getSource() == delete){
                String sql1 = null, sql2 = null;
                if(userType == 0) {
                    sql1 = String.format("DELETE FROM `borrower` WHERE id = %d;", id);
                    sql2 = String.format("DELETE FROM `user` WHERE id = %d AND position = 'b';", id);
                } else if(userType == 1){
                    sql1 = String.format("DELETE FROM `librarian` WHERE id = %d;", id);
                    sql2 = String.format("DELETE FROM `user` WHERE id = %d AND position = 'l';", id);
                }

                if(deleteRecord(sql1, sql2)){
                    if(userType == 0){
                        String sql3 = String.format("UPDATE `bookCopy` SET borrowerID = -1 WHERE borrowerID = %d;", id);
                        DBManager.edit(sql3);
                        admin.removeBorrower(id);
                    }
                    else if(userType == 1)
                        admin.removeLibrarian(id);
                    ((DefaultTableModel)t.getModel()).removeRow(row);
                }
                return;
            }
            editPanel.setVisible(true);
        }
    }
}
