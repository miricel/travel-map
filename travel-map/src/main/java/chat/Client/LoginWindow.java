package chat.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginWindow extends JFrame {
    private final Client client;
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");

    public LoginWindow(){
        super("Login");

        this.client = new Client("localhost",8818);
        client.connect();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel ,BoxLayout.Y_AXIS));
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        getContentPane().add(panel,BorderLayout.CENTER);

        pack();

        setVisible(true);
    }

    private void doLogin() {
        String user = usernameField.getText();
        String password = passwordField.getText();

        try {
            if(client.login(user,password)){
                UserListPane userListPane = new UserListPane(client);
                JFrame frame = new JFrame("User List");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400,500);

                frame.getContentPane().add(userListPane, BorderLayout.CENTER);
                frame.setVisible(true);
                setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(this,"Invalid login/password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
    }
}
