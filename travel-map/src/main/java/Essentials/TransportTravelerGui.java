package Essentials;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransportTravelerGui extends JPanel {

    private final Connection con;
    private final int transportId, travelerId;
    private Transport transport;
    private JPanel type;
    private JPanel departure;
    private JPanel arrival;
    private JPanel price;
    private  int width = 245;
    private int height = 150;
    private ResultSet transportData;
    private  Font font = null;

    public TransportTravelerGui(Connection con, int ticketId, int travelerId){
        this.con = con;
        this.transportId = ticketId;
        this.travelerId = travelerId;
        transport = new Transport(con,ticketId);
        setBackground(new Color(200,200,200,150));
        setLayout( new BoxLayout(this,BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(700,height));

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, TransportGui.class.getResourceAsStream("/romi.ttf"));
            transportData = transport.getAllData();
            type = typeFabric();
            departure = departureFabric();
            arrival = arrivalFabric();
            price = priceFabric();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        add(type);
        add(departure);
        add(arrival);
        add(price);
    }

    private JPanel priceFabric() throws SQLException, IOException {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(110, 110));
        panel.setBorder(new EmptyBorder(0,0,0,30));


        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text,BoxLayout.Y_AXIS));

        JLabel pricelbl = new JLabel(transportData.getDouble("price")+"$");
        pricelbl.setFont(font);
        pricelbl.setForeground(Color.black);
        pricelbl.setAlignmentX(CENTER_ALIGNMENT);
        text.add(pricelbl);
        int all = transportData.getInt("max_tickets");
        int taken = transportData.getInt("taken_tickets");
        JLabel placelbl = new JLabel((all-taken)+"/"+all+" left");
        placelbl.setFont(font);
        placelbl.setForeground(Color.black);
        placelbl.setAlignmentX(CENTER_ALIGNMENT);
        text.add(placelbl);
        JLabel booklbl = new JLabel("BOOK NOW");
        booklbl.setFont(font);
        booklbl.setForeground(Color.black);
        booklbl.setAlignmentX(CENTER_ALIGNMENT);
        text.add(booklbl);

        BackgroundBtn circle = new BackgroundBtn();
        circle.setRolloverEnabled(false);
        circle.setSize(112,120);
        circle.setLayout(new GridBagLayout());
        circle.setContentAreaFilled(false);
        circle.setBorder(new EmptyBorder(0,15,0,15));
        circle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if((all-1) > 0)
                        transport.setIntColumn("max_tickets", all-1);
                    else transport.deleteRow();
                    Ticket.newTicket(transportId, transport.getIntColumn("agencies_id"), travelerId,transport.getStringColumn("type"));
                    JOptionPane.showMessageDialog(null, "Ticket booked!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        circle.add(text);
        panel.add(circle);
        return panel;
    }

    private JPanel arrivalFabric() throws SQLException {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBorder(new EmptyBorder(17,5,0,20));

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");
        LocalDateTime arrivalDateTime = null;

        arrivalDateTime = transport.getDateTimeColumn("arrival");

        JLabel date = new JLabel(arrivalDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JLabel hour = new JLabel(""+arrivalDateTime.format(timeFormat) );
        JLabel day = new JLabel(arrivalDateTime.getDayOfWeek().toString());
        JLabel city = new JLabel(transportData.getString("destination_city"));
        //  JLabel year = new JLabel(+"");


        font = font.deriveFont(20f);
        day.setFont(font.deriveFont(Font.BOLD));
        hour.setFont(font.deriveFont(Font.ITALIC));
        date.setFont(font);
        city.setFont(font.deriveFont(Font.BOLD,22f));
        date.setBorder(new EmptyBorder(3,0,0,0));
        //hour.setBorder(new EmptyBorder(-5,0,0,0));
        date.setForeground(Color.black);
        hour.setForeground(Color.black);
        day.setForeground(Color.black);
        city.setForeground(Color.black);
        day.setAlignmentX(CENTER_ALIGNMENT);
        hour.setAlignmentX(CENTER_ALIGNMENT);
        date.setAlignmentX(CENTER_ALIGNMENT);
        city.setAlignmentX(CENTER_ALIGNMENT);
        //  year.setForeground(Color.pink);

        panel.add(day);
        panel.add(date);
        panel.add(hour);
        panel.add(city);

        return panel;
    }

    private JPanel departureFabric() throws SQLException {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBorder(new EmptyBorder(17,20,0,15));

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");
        LocalDateTime departureDateTime = null;

        departureDateTime = transport.getDateTimeColumn("departure");

        JLabel date = new JLabel(departureDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JLabel hour = new JLabel(""+departureDateTime.format(timeFormat) );
        JLabel day = new JLabel(departureDateTime.getDayOfWeek().toString());
        JLabel city = new JLabel(transportData.getString("departure_city"));
        //  JLabel year = new JLabel(+"");


        font = font.deriveFont(20f);
        day.setFont(font.deriveFont(Font.BOLD));
        hour.setFont(font.deriveFont(Font.ITALIC));
        date.setFont(font);
        city.setFont(font.deriveFont(Font.BOLD,22f));
        date.setBorder(new EmptyBorder(3,0,0,0));
        //hour.setBorder(new EmptyBorder(-5,0,0,0));
        date.setForeground(Color.black);
        hour.setForeground(Color.black);
        day.setForeground(Color.black);
        city.setForeground(Color.black);
        day.setAlignmentX(CENTER_ALIGNMENT);
        hour.setAlignmentX(CENTER_ALIGNMENT);
        date.setAlignmentX(CENTER_ALIGNMENT);
        city.setAlignmentX(CENTER_ALIGNMENT);
        //  year.setForeground(Color.pink);

        panel.add(day);
        panel.add(date);
        panel.add(hour);
        panel.add(city);

        return panel;
    }

    private JPanel typeFabric() throws SQLException, IOException {

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(height+20, height));
        panel.setBorder(new EmptyBorder(15,30,0,27));

        String transportphoto = transportData.getString("type");
        String transportMean;

        if(transportphoto.equalsIgnoreCase("plane")) {
            transportphoto = "resources/planemini.png";
            transportMean = "PLANE";
        }
        else if(transportphoto.equalsIgnoreCase("bus")) {
            transportphoto = "resources/busmini.png";
            transportMean = "BUS";
        }
        else if(transportphoto.equalsIgnoreCase("train")) {
            transportphoto = "resources/trainmini.png";
            transportMean = "TRAIN";
        }
        else if(transportphoto.equalsIgnoreCase("ferryboat")) {
            transportphoto = "resources/boatmini.png";
            transportMean = "FERRYBOAT";
        }
        else  {
            transportphoto = "resources/agency.png";
            transportMean = "";
        }

        BufferedImage foto = resize(ImageIO.read(new File(transportphoto)),80,80);

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
        lblimage.setPreferredSize(new Dimension(90,120));
        lblimage.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(lblimage);


        JLabel lbltransport = new JLabel(transportMean);
        lbltransport.setForeground(Color.black);
        lbltransport.setFont(font.deriveFont(21f));
        lbltransport.setBorder(new EmptyBorder(3,0,0,0));
        lbltransport.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(lbltransport);

        return panel;
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



    protected class BackgroundPanel extends JPanel{
        private final int width = 110;
        private final int height = 110;
        private BufferedImage background = resize(ImageIO.read(new File("resources/circle.png")),110,110) ;


        public BackgroundPanel() throws IOException {
            setBounds(10,20,110,110);
        }

        private  BufferedImage resize(BufferedImage img, int newW, int newH) {
            Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            return dimg;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = getWidth() - background.getWidth();
                int y = getHeight() - background.getHeight();
                g2d.drawImage(background, x, y, this);
                g2d.dispose();
            }
        }
    }

    protected class BackgroundBtn extends JButton{
        private final int width = 110;
        private final int height = 110;
        private BufferedImage background = resize(ImageIO.read(new File("resources/circle.png")),110,110) ;


        public BackgroundBtn() throws IOException {
            setBounds(10,20,110,110);
            setOpaque(false);
        }

        private  BufferedImage resize(BufferedImage img, int newW, int newH) {
            Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            return dimg;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                int x = getWidth() - background.getWidth();
                int y = getHeight() - background.getHeight();
                g2d.drawImage(background, x, y, this);
                g2d.dispose();
            }
        }
    }

}
