import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener{

    private JTextField login;
    private JPasswordField password;

    public LoginWindow(){
        JLabel nameLabel = new JLabel("Library Management System");
        JLabel loginL = new JLabel("Login");
        login = new JTextField("");
        JLabel passwordL = new JLabel("Password");
        password = new JPasswordField();
        JButton submitBtn = new JButton("Login");
        submitBtn.addActionListener(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(nameLabel);
        mainPanel.add(Box.createVerticalGlue());

        JPanel compPanel = new JPanel();
        compPanel.setLayout(new GridLayout(2, 2, 5, 5));
        loginL.setHorizontalAlignment(JLabel.RIGHT);
        compPanel.add(loginL);
        compPanel.add(login);
        compPanel.add(passwordL);
        passwordL.setHorizontalAlignment(JLabel.RIGHT);
        compPanel.add(password);

        mainPanel.add(compPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(submitBtn);
        mainPanel.add(Box.createVerticalGlue());
        this.add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width - this.getSize().width) / 2, (dim.height - this.getSize().height) / 2);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
