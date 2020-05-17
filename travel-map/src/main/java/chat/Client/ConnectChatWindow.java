package chat.Client;



import User.Utility;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class ConnectChatWindow {
    private final Client client;
    JButton loginButton = new JButton("LOGIN");
    private JFrame frame = new JFrame();
    private Connection con;

    public ConnectChatWindow(Connection con){

        this.con =con;
        this.client = new Client("localhost",8818,con);
        client.connect();

        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        Color color = new Color(225, 225, 225);
        color = transparentColor(color, 100);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setName("username");
        usernamePanel.setBounds(83, 61, 304, 28);
        usernamePanel.setBackground(color);
        frame.getContentPane().add(usernamePanel);

        JPanel passPanel = new JPanel();
        passPanel.setName("password");
        passPanel.setBounds(83, 115, 304, 28);
        passPanel.setBackground(color);
        frame.getContentPane().add(passPanel);

        final JTextPane textUsername = new JTextPane();
        textUsername.setBounds(83, 61, 304, 28);
        textUsername.setOpaque(false);
        frame.getContentPane().add(textUsername);

        final JPasswordField textPassword = new JPasswordField();
        textPassword.setBounds(83, 115, 304, 28);
        textPassword.setBorder(null);
        textPassword.setOpaque(false);
        frame.getContentPane().add(textPassword);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setBounds(90, 44, 91, 15);
        frame.getContentPane().add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBorder(null);
        lblPassword.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(90, 98, 70, 15);
        frame.getContentPane().add(lblPassword);

        loginButton = new JButton("LOG IN");
        loginButton.setBorder(null);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(204, 51, 102));
        loginButton.setFont(new Font("Liberation Sans", Font.BOLD, 12));
        loginButton.setBounds(182, 193, 117, 28);
        frame.getContentPane().add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = textUsername.getText();

                System.out.println(user);
                String password = null;
                try {
                    password = Utility.hashPassword(textPassword.getText());
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
                doLogin(user,password);
            }
        });

        JLabel lblBackground = new JLabel();
        lblBackground.setBorder(null);

        ImageIcon i ;
      //  Image scaled = resizedImage(460, 300, i);
        i = new ImageIcon(this.getClass().getResource("/pink.jpg"));

        lblBackground.setIcon(i);
        lblBackground.setBounds(0, -48, 453, 336);
        frame.getContentPane().add(lblBackground);

        frame.setVisible(true);
    }
    private Color transparentColor(Color color, int transparencyGradient) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparencyGradient);
    }

    public ConnectChatWindow(String username, String password,Connection con) {

        this.con = con;
        this.client = new Client("localhost",8818,con);
        client.connect();
        doLogin(username,password);
    }

    private void doLogin(String user, String password) {

        try {
            if(client.login(user,password)){

                frame.setVisible(false);
                UserListPane userListPane = new UserListPane(client,con);
                userListPane.paint();
            }
            else {
                JOptionPane.showMessageDialog(frame,"Invalid login/password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
