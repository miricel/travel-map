package chat.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessagePane extends JPanel implements MessageListener {
    private final Client client;
    private final String user;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> messageList = new JList<>(listModel);
    private JTextField inputField = new JTextField();

    public MessagePane(final Client client, final String user) {
        this.client = client;
        this.user = user;

        client.addMessageListener(this);

        setLayout(new BorderLayout());
        add(new JScrollPane(messageList), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                client.msg(user,text);
                inputField.setText("");
                listModel.addElement("You: "+text);
            }
        });
    }

    @Override
    public void onMessage(String from, String text) {
        if(user.equalsIgnoreCase(from)){
            String line = from + ": " + text;
            listModel.addElement(line);
        }
    }
}
