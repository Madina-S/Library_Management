import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminFrame extends JFrame implements ActionListener{

    private Admin admin;

    private JTabbedPane tabbedPane;
    private JButton add, delete, edit;
    private EditPanel editPanel;

    public AdminFrame(Admin admin) {
        this.admin = admin;

        tabbedPane = new JTabbedPane() {
            @Override
            public void setSelectedIndex(int index) {
                JScrollPane sc = (JScrollPane) tabbedPane.getSelectedComponent();
                if (sc != null) {
                    JViewport view = sc.getViewport();
                    JTable t = (JTable) view.getComponent(0);
                    t.clearSelection();
                }
                super.setSelectedIndex(index);
            }
        };

        addTable("Borrowers", admin.getBorrowers());
        addTable("Librarians", admin.getLibrarians());

        BorderLayout layout = new BorderLayout();
        layout.setVgap(10);
        JPanel main = new JPanel();
        main.setLayout(layout);
        main.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add = new JButton("Add");
        add.addActionListener(this);
        delete = new JButton("Delete");
        delete.addActionListener(this);
        edit = new JButton("Edit");
        edit.addActionListener(this);
        btnPanel.add(add);
        btnPanel.add(edit);
        btnPanel.add(delete);

        main.add(btnPanel, BorderLayout.PAGE_START);
        main.add(tabbedPane, BorderLayout.CENTER);

        editPanel = new EditPanel(admin);

        setLayout(new BorderLayout());
        add(main, BorderLayout.CENTER);
        add(editPanel, BorderLayout.LINE_START);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private void addTable(String name, ArrayList<User> objects){
        JTable table = new JTable(){
            public boolean isCellEditable(int row, int column) {
                return false;
            };

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component c = super.prepareRenderer(renderer, row, column);
                if(isRowSelected(row)){
                    c.setBackground(new Color(153, 221, 254));
                    return c;
                }
                c.setBackground(row % 2 == 0 ? Color.white : new Color(253, 255, 229));
                return c;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Object[][] row = null;
        String[] column = {"ID", "Name", "Surname", "Phone Number"};
        DefaultTableModel dtm = new DefaultTableModel(row, column);
        table.setModel(dtm);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(153, 221, 254));

        table.setRowHeight(28);

        table.getColumnModel().getColumn(0).setWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        for(int i = 0; i < objects.size(); i++){
            User user = (User) objects.get(i);
            dtm.addRow(new Object[]{user.getID(), user.getName(), user.getSurname(), user.getPhoneNumber()});
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.add(name, sp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == add){
            editPanel.set();
            editPanel.setVisible(true);
            return;
        }

        JScrollPane sc = (JScrollPane) tabbedPane.getSelectedComponent();
        int row;
        if (sc != null) {
            JViewport view = sc.getViewport();
            JTable t = (JTable) view.getComponent(0);
            t.clearSelection();
            row = t.getSelectedRow();
            if(row == -1)
                return;

            int id = Integer.parseInt(t.getModel().getValueAt(row, 0).toString());
            if(e.getSource() == edit){
                String name = t.getModel().getValueAt(row, 1).toString();
                String surname = t.getModel().getValueAt(row, 2).toString();
                String phoneNumber = t.getModel().getValueAt(row, 3).toString();
                editPanel.set(id, name, surname, phoneNumber);
            }else if(e.getSource() == delete){
                deleteRecord(id);
                return;
            }
            editPanel.setVisible(true);
        }
    }
}
