package chat.Client;
import Component.myRow;
import Component.myRowJPanel;
import Component.BackgroundList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserListPane extends JFrame implements UserStatusListener {

    private final Client client;
    private DefaultListModel<JPanel> userListModel = new DefaultListModel<>();
    private ArrayList<String > elements = new ArrayList<>();
    private JList<JPanel> userListUI = new JList<>(userListModel);
    Lock lock = new ReentrantLock();
    private Connection con;
    private BackgroundList backgroundList;


    public UserListPane(final Client client,Connection con) {

        this.con = con;
        this.client = client;
        this.client.addUserStatusListener(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
                client.disconnect();
            }
        });
        setBounds(50, 50, 414, 450);
        setTitle("Chat");
        setVisible(true);
        getContentPane().setLayout(new BorderLayout());
    }

    public void paint(){

        final JFrame thisFrame = this;
         userListUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() > 1){  //double-click?
                    JPanel user = userListUI.getSelectedValue();
                    new MessagePane( client,user,thisFrame,con);
                    setVisible(false);
                }
                else{
                    JPanel user = userListUI.getSelectedValue();
                    System.out.println(">>"+user.getName());
                }
            }
        });

        userListUI.setOpaque(false);

        ImageIcon i = new ImageIcon(this.getClass().getResource("/pink.jpg"));


        design();
    }

    private Color transparentColor(Color color, int transparencyGradient) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparencyGradient);

    }

    private void design() {

      /*  Color color = new Color(0, 0, 0);
        color = transparentColor(color, 200);
       */
        ArrayList<String > users = new ArrayList<>();
        Statement st = null;
        ResultSet rs;

        try {
            st=con.createStatement();
            rs=st.executeQuery("select * from travelers");
            while(rs.next())  {

                String username = rs.getString("username");
                if( !username.equals(client.getUsername()) ) {
                    JLabel label = new JLabel(username);
                    label.setPreferredSize(new Dimension(400, 30));
                    label.setFont(new Font("Liberation Sans", Font.BOLD, 15));
                    label.setForeground(new Color(100, 20, 60));
                    label.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 5));
                    System.out.println(">>>"+username);
                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(300, 30));
                    panel.setBackground(Color.pink);
                    panel.setName(username);
                    panel.setBorder(BorderFactory.createLineBorder(new Color( 200,110,100),2));
                    panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
                    panel.add(label);

                    userListModel.addElement(panel);
                }
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
       /* myRow row = new myRow(users);
        userListUI.setCellRenderer(row);
        */
        myRowJPanel row = new myRowJPanel();
        userListUI.setCellRenderer(row);
        JScrollPane scroll = new JScrollPane(userListUI);
        scroll.setBounds(0,27,400,450);
        //  Color color = new Color(225, 100, 225);
        // color = transparentColor(color, 100);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        backgroundList = new BackgroundList(scroll);
        backgroundList.setBounds(0,27,400,450);
        getContentPane().add(backgroundList);

        JPanel myHeader = new JPanel();
        myHeader.setBounds(0, 0, 400, 246);
        myHeader.setLayout(null);
        myHeader.setOpaque(false);
        getContentPane().add(myHeader);

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(204, 51, 102));
        panelHeader.setBounds(0, 0, 400, 27);
        myHeader.add(panelHeader);

        JLabel lblMenu = new JLabel("FRIENDS");
        lblMenu.setForeground(new Color(255, 255, 255));
        lblMenu.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        lblMenu.setBounds(0, 0, 400, 27);
        lblMenu.setHorizontalAlignment(JTextField.CENTER);
        panelHeader.add(lblMenu);

        final JPanel panelMenu = new JPanel();
        panelMenu.setLayout(null);
        panelMenu.setBorder(null);
        panelMenu.setVisible(false);
        panelMenu.setBackground(new Color(190,120,110));
        panelMenu.setBounds(0, 27, 139, 217);
        myHeader.add(panelMenu);

    }

    @Override
    public void online(String username) {
        lock.lock();
        elements.add(username);
        lock.unlock();
    }

    @Override
    public void offline(String username) {
        lock.lock();
        elements.remove(username);
        lock.unlock();
    }
}
