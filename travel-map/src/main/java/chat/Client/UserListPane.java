package chat.Client;
import Component.myRow;
import Component.myRowJPanel;
import Component.BackgroundList;
import User.Traveller.Traveler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Blob;
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
        setBounds(50, 50, 510, 710);
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
                    label.setPreferredSize(new Dimension(400, 53));
                    label.setFont(new Font("Liberation Sans", Font.BOLD, 15));
                    label.setForeground(new Color(100, 20, 60));
                    label.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 5));
                    System.out.println(">>>"+username);
                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(300, 53));
                    panel.setBackground(Color.pink);
                    panel.setName(username);
                    panel.setBorder(BorderFactory.createLineBorder(new Color( 200,110,100),2));
                    panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

                    com.mysql.jdbc.Blob blob = (com.mysql.jdbc.Blob) rs.getBlob("profilePic");
                    InputStream is = blob.getBinaryStream();
                    BufferedImage b = ImageIO.read(is);
                    Image image = b;
                    ImageIcon ic = new ImageIcon(image);

                    BufferedImage bi = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics g = bi.createGraphics();
                    ic.paintIcon(null, g, 0,0);
                    g.dispose();

                    BufferedImage foto = resize(bi,49,49);

                    int diameter = Math.min(foto.getWidth(), foto.getHeight());
                    BufferedImage mask = new BufferedImage(foto.getWidth(), foto.getHeight(), BufferedImage.TYPE_INT_ARGB);

                    Graphics2D g2d = mask.createGraphics();
                    applyQualityRenderingHints(g2d);
                    g2d.fillOval(0, 0, diameter - 1, diameter - 1);
                    g2d.dispose();

                    BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
                    g2d = masked.createGraphics();
                    applyQualityRenderingHints(g2d);
                    int x = (diameter - foto.getWidth()) / 2;
                    int y = (diameter - foto.getHeight()) / 2;
                    g2d.drawImage(foto, x, y, null);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
                    g2d.drawImage(mask, 0, 0, null);
                    g2d.dispose();

                    JLabel lblimage = new JLabel(new ImageIcon(masked));
                    lblimage.setPreferredSize(new Dimension(70,56));
                    lblimage.setBorder(new EmptyBorder(3,10,3,10));
                    panel.add(lblimage);
                    panel.add(label);

                    userListModel.addElement(panel);
                }
            }

            rs=st.executeQuery("select * from agencies");
            while(rs.next())  {

                String username = rs.getString("username");
                if( !username.equals(client.getUsername()) ) {
                    JLabel label = new JLabel(username);
                    label.setPreferredSize(new Dimension(400, 53));
                    label.setFont(new Font("Liberation Sans", Font.BOLD, 15));
                    label.setForeground(new Color(100, 20, 60));
                    label.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 5));
                    System.out.println(">>>"+username);
                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(300, 53));
                    panel.setBackground(new Color(255, 229, 180));
                    panel.setName(username);
                    panel.setBorder(BorderFactory.createLineBorder(new Color( 200,110,100),2));
                    panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
                    com.mysql.jdbc.Blob blob = (com.mysql.jdbc.Blob) rs.getBlob("profilePic");
                    InputStream is = blob.getBinaryStream();
                    BufferedImage b = ImageIO.read(is);
                    Image image = b;
                    ImageIcon ic = new ImageIcon(image);

                    BufferedImage bi = new BufferedImage(ic.getIconWidth(), ic.getIconHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics g = bi.createGraphics();
                    ic.paintIcon(null, g, 0,0);
                    g.dispose();

                    BufferedImage foto = resize(bi,49,49);

                    int diameter = Math.min(foto.getWidth(), foto.getHeight());
                    BufferedImage mask = new BufferedImage(foto.getWidth(), foto.getHeight(), BufferedImage.TYPE_INT_ARGB);

                    Graphics2D g2d = mask.createGraphics();
                    applyQualityRenderingHints(g2d);
                    g2d.fillOval(0, 0, diameter - 1, diameter - 1);
                    g2d.dispose();

                    BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
                    g2d = masked.createGraphics();
                    applyQualityRenderingHints(g2d);
                    int x = (diameter - foto.getWidth()) / 2;
                    int y = (diameter - foto.getHeight()) / 2;
                    g2d.drawImage(foto, x, y, null);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
                    g2d.drawImage(mask, 0, 0, null);
                    g2d.dispose();

                    JLabel lblimage = new JLabel(new ImageIcon(masked));
                    lblimage.setPreferredSize(new Dimension(70,56));
                    lblimage.setBorder(new EmptyBorder(3,10,3,10));
                    panel.add(lblimage);
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
        backgroundList.setBounds(0,27,520,645);
        getContentPane().add(backgroundList);

        JPanel myHeader = new JPanel();
        myHeader.setBounds(0, 0, 500, 246);
        myHeader.setLayout(null);
        myHeader.setOpaque(false);
        getContentPane().add(myHeader);

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(204, 51, 102));
        panelHeader.setBounds(0, 0, 512, 27);
        myHeader.add(panelHeader);

        JLabel lblMenu = new JLabel("FRIENDS");
        lblMenu.setForeground(new Color(255, 255, 255));
        lblMenu.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        lblMenu.setBounds(0, 0, 512, 27);
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

    private void applyQualityRenderingHints(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    }
    private static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
