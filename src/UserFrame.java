import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public abstract class UserFrame extends Frame implements ActionListener{

    protected JTabbedPane tabbedPane;
    protected JButton add, delete, edit;

    public UserFrame() {
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

        setLayout(new BorderLayout());
        add(main, BorderLayout.CENTER);

        setSize(new Dimension(800, 700));
        setOptimalLocation();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DBManager.closeConnection();
            }
        });
    }

    protected void addObjectToTable(DefaultTableModel dtm, ArrayList<User> items){
        for(User item : items){
            dtm.addRow(new Object[]{item.getID(), item.getName(), item.getSurname(), item.getPhoneNumber()});
        }
    }

    protected DefaultTableModel addTable(String name, String[] column ){
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

        /*JTableHeader header = table.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(191, 209, 229));
        HEADER BORDER COLOR SHOULD BE CHANGED*/

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Object[][] row = null;
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

        JScrollPane sp = new JScrollPane(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.add(name, sp);
        return dtm;
    }

    protected boolean deleteRecord(String... sqls) {
        for(String sql : sqls){
            if(sql.isEmpty())
                return false;
            if(!DBManager.delete(sql))
                return false;
        }

        return true;
    }

    public void enableButtons(boolean enabled){
        delete.setEnabled(enabled);
        edit.setEnabled(enabled);
        add.setEnabled(enabled);
    }
}
