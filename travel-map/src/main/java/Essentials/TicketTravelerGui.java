package Essentials;
import Main.Main;
import User.Traveller.Traveler;
import com.mysql.jdbc.Connection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URL;

import static java.awt.Font.PLAIN;

public class TicketTravelerGui extends JPanel {

    private final int id;
    private final Connection con;
    private Ticket ticket;
    private JPanel customer;
    private JPanel date;
    private JPanel details;
    private JPanel accept;
    private int width = 230;
    private int height = 125;

    public TicketTravelerGui(Connection con,int id) throws SQLException {
        this.con = con;
        this.id = id;
        setBorder(new EmptyBorder(0,1,0,1));
        setOpaque(false);

        ticket = new Ticket(con,id);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.pink,2));
        try {
            details = detailsFabric();
        } catch (IOException e) {
            e.printStackTrace();
        }
        date = dateFabric();
        add(details);
        add(date);

    }

    private JPanel detailsFabric() throws SQLException, IOException {
        JPanel panel = new JPanel();
        panel.setBackground(Color.pink);
        panel.setBorder(new LineBorder(new Color(200,180,210)));
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(width,height));

        //// transport mean

        String transportphoto = ticket.getStringColumn("transport_mean");

        if(transportphoto.equalsIgnoreCase("plane"))
            transportphoto = "resources/planemini.png";
        else if(transportphoto.equalsIgnoreCase("bus"))
            transportphoto = "resources/busmini.png";
        else if(transportphoto.equalsIgnoreCase("train"))
            transportphoto = "resources/trainmini.png";
        else if(transportphoto.equalsIgnoreCase("ferryboat"))
            transportphoto = "resources/boatmini.png";
        else  transportphoto = "resources/agency.png";


        BufferedImage foto = resize(ImageIO.read(new File(transportphoto)),75,75);

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
        lblimage.setPreferredSize(new Dimension(90,125));
        lblimage.setBorder(new EmptyBorder(10,10,10,10));
        panel.add(lblimage);

        ////

        ResultSet transportData = new Transport(con,ticket.getIntColumn("transport_id")).getAllData();

        /// from-to
        JLabel fromTo = new JLabel(transportData.getString("departure_city")+" - " +transportData.getString("destination_city"));
        fromTo.setForeground(Color.black);
        setBorder(new EmptyBorder(-5,0,-5,0));
        fromTo.setPreferredSize(new Dimension(125,20));

        //// status
        JLabel status = new JLabel();
        String st = ticket.getStringColumn("status");
        if(st.equals("acepted"))
            status.setForeground(new Color(73, 168, 25));
        else if(st.equals("Rejected"))
            status.setForeground(Color.red);
        status.setText(st);
        status.setForeground(Color.black);
        status.setFont(new Font("Liberation Sans", Font.ITALIC, 14));
        setBorder(new EmptyBorder(0,0,-5,0));
        status.setPreferredSize(new Dimension(130,20));

        JLabel price = new JLabel(transportData.getDouble("price")+"$");
        price.setForeground(Color.black);
        price.setFont(new Font("Liberation Sans", PLAIN, 16));
        setBorder(new EmptyBorder(-5,0,-5,0));
        price.setPreferredSize(new Dimension(125,20));

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setPreferredSize(new Dimension(130,70));
        details.setBorder(new EmptyBorder(10,10,10,3));
        // details.setLayout(new BoxLayout(details,BoxLayout.PAGE_AXIS));
        details.add(fromTo);
        details.add(price);
        details.add(status);
        panel.add(details);

        return panel;
    }
    private JPanel dateFabric() throws SQLException {

        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setBorder(new LineBorder(new Color(200,180,210)));
        panel.setPreferredSize(new Dimension(width,height+10));
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");
        LocalDateTime departureDateTime = null;
        LocalDateTime arrivalDateTime = null;

        departureDateTime = ticket.getDateTimeColumn("departure");
        arrivalDateTime = ticket.getDateTimeColumn("arrival");

        JPanel departure = new JPanel();

        JLabel date = new JLabel(departureDateTime.format(DateTimeFormatter.ofPattern("dd/MM")));
        JLabel hour = new JLabel("at "+departureDateTime.format(timeFormat) );
        JLabel day = new JLabel(departureDateTime.getDayOfWeek().toString()+", "+departureDateTime.getYear());
        //  JLabel year = new JLabel(+"");

        departure.setOpaque(false);
        departure.setPreferredSize(new Dimension(110,70));
        departure.setBorder(new EmptyBorder(30,2,10,3));
        date.setFont(new Font("Liberation Sans", Font.BOLD, 26));
        hour.setFont(new Font("Liberation Sans", Font.ITALIC, 16));
        date.setBorder(new EmptyBorder(-5,0,-5,0));
        hour.setBorder(new EmptyBorder(-5,0,0,0));
        date.setForeground(Color.pink);
        hour.setForeground(Color.pink);
        day.setForeground(Color.pink);
        //  year.setForeground(Color.pink);

        departure.add(day);
        departure.add(date);
        departure.add(hour);
        // departure.add(year);

        JPanel arrival = new JPanel();
        arrival.setOpaque(false);
        arrival.setPreferredSize(new Dimension(110,70));
        arrival.setBorder(new EmptyBorder(30,3,10,2));

        date  = new JLabel(arrivalDateTime.format(DateTimeFormatter.ofPattern("dd/MM")));
        hour = new JLabel("at "+arrivalDateTime.format(timeFormat));
        day = new JLabel(arrivalDateTime.getDayOfWeek().toString()+", "+arrivalDateTime.getYear());
        date.setFont(new Font("Liberation Sans", Font.BOLD, 26));
        hour.setFont(new Font("Liberation Sans", Font.ITALIC, 16));
        date.setBorder(new EmptyBorder(-5,0,-5,0));
        hour.setBorder(new EmptyBorder(-5,0,0,0));
        date.setForeground(Color.pink);
        hour.setForeground(Color.pink);
        day.setForeground(Color.pink);
        arrival.add(day);
        arrival.add(date);
        arrival.add(hour);


        panel.add(departure);
        panel.add(arrival);
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


}