package chat.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Component.BackgroundList;
import Component.myRowJPanel;

public class MessagePane extends JFrame implements MessageListener {
    private final Client client;
    private final String dest;
    private final JFrame prevFrame;
    private final String username;
    private BackgroundList backgroundList;
    private DefaultListModel<JPanel> listModel = new DefaultListModel<>();
    private JList<JPanel> messageList = new JList<>(listModel);
    private JTextField inputField = new JTextField();
    private Connection con;

    public MessagePane(final Client client, final JPanel jPanel, final JFrame prevFrame, final Connection con) {
        this.client = client;
        this.dest = jPanel.getName();
        this.prevFrame = prevFrame;
        this.con = con;
        username = client.getUsername();

        readOldMessages();

        client.addMessageListener(this);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                try {
                    client.msg(dest,text);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                inputField.setText("");
                JPanel panel= sentMessage(text);
                listModel.addElement(panel);
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
               // client.disconnect();
                prevFrame.setVisible(true);
                dispose();
            }
        });
        setBounds(50, 50, 414, 450);
        setVisible(true);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputField, BorderLayout.SOUTH);

        design();
    }

    private void readOldMessages() {

        Statement mystate = null;
        int conv = 0 ;
        try {
            mystate = con.createStatement();

            String query = "SELECT id FROM conversation WHERE ((user1 = '" + username + "' AND user2 = '" + dest + "') " +
                    "OR (user1 = '" + dest + "' AND user2 = '" + username + "'))";
            ResultSet myRS = mystate.executeQuery(query);
            if (myRS.next())
                conv = myRS.getInt("id");
            else return;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(mystate != null) mystate.close();
            }catch(Exception e)
            {
                System.out.println(">Error: " + e);
            }
        }


        Statement st = null;
        ResultSet rs;
        String dest,text;
        JPanel panel;
        try {
            st=con.createStatement();
            rs=st.executeQuery("select * from messages WHERE conversationID = "+conv);
            while(rs.next())  {

                dest = rs.getString("to");
                text = rs.getString("text");
                if( dest.equals(client.getUsername()) )
                   panel = recivedMessage(text);
                else panel = sentMessage(text);

                listModel.addElement(panel);
            }

        }catch(Exception e)
        {
            System.out.println("Error:::: " + e);
        }
        finally{
            try {
                if(st != null) st.close();
            }catch(Exception e)
            {
                System.out.println(">Error: " + e);
            }
        }

    }

    private JPanel sentMessage(String text) {

        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(400, 30));
        label.setFont(new Font("Liberation Sans", Font.BOLD, 15));
        label.setForeground(Color.pink);
        label.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 5));
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 30));
        panel.setBackground(new Color(100, 20, 60));
        panel.setName(username);
        panel.setBorder(BorderFactory.createLineBorder(new Color( 200,110,100),2));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        return panel;
    }

    private JPanel recivedMessage(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(400, 30));
        label.setFont(new Font("Liberation Sans", Font.BOLD, 15));
        label.setForeground(new Color(100, 20, 60));
        label.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 5));
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 30));
        panel.setBackground(Color.pink);
        panel.setName(username);
        panel.setBorder(BorderFactory.createLineBorder(new Color( 200,110,100),2));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(label);
        return panel;
    }

    private void design() {

        myRowJPanel row = new myRowJPanel();
        messageList.setCellRenderer(row);
        messageList.setOpaque(false);
        JScrollPane scroll = new JScrollPane(messageList);
        scroll.setBounds(0,27,400,380);
        //  Color color = new Color(225, 100, 225);
        // color = transparentColor(color, 100);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        backgroundList = new BackgroundList(scroll);
        backgroundList.setBounds(0,27,400,368);
        getContentPane().add(backgroundList);

        JPanel myHeader = new JPanel();
        myHeader.setBounds(0, 0, 400, 246);
        myHeader.setLayout(null);
        myHeader.setOpaque(false);
        getContentPane().add(myHeader);

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(190, 70, 102));
        panelHeader.setBounds(0, 0, 400, 27);
        myHeader.add(panelHeader);

        JLabel lblMenu = new JLabel(dest);
        lblMenu.setForeground(new Color(255, 255, 255));
        lblMenu.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        lblMenu.setBounds(0, 0, 400, 27);
        lblMenu.setHorizontalAlignment(JTextField.CENTER);
        panelHeader.add(lblMenu);

        JButton btnBack = new JButton("Back");
        btnBack.setBorderPainted(false);
        btnBack.setBounds(-13, 0, 100, 27);
        panelHeader.add(btnBack);
        btnBack.setContentAreaFilled(false);
        btnBack.setForeground(new Color(255, 255, 255));
        btnBack.setFont(new Font("Jamrul",Font.PLAIN, 12));

        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() > 1){  //double-click?
                   prevFrame.setVisible(true);
                   setVisible(false);
                }
            }
        });

        final JPanel panelMenu = new JPanel();
        panelMenu.setLayout(null);
        panelMenu.setBorder(null);
        panelMenu.setVisible(false);
        panelMenu.setBackground(new Color(190,120,110));
        panelMenu.setBounds(0, 27, 139, 217);
        myHeader.add(panelMenu);
    }

    @Override
    public void onMessage(String from, String text) {
        if(dest.equalsIgnoreCase(from)){
          //  String line = from + ": " + text;
            JPanel panel = recivedMessage(text);
            listModel.addElement(panel);
        }
    }
}
