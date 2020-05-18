package Component;

import Essentials.Ticket;
import Essentials.TicketGui;
import com.mysql.jdbc.Connection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddElement extends JPanel {


    // JPanel to hold all rows. uses gridlayout that has 1 column and variable number
    // of rows
    private JPanel rowHolderPanel;
    private int height;
    private int width;

    public AddElement(int a,int b,int c,int d, int width, int height, int l,int r, int u) throws SQLException {
        // outerPanel is a wrapper or container JPanel that is
        // held by JScrollPane's viewport that holds the rowHolderPanel in
        // a BorderLayout.PAGE_START location, so the rows don't expand unnecessarily
        
        this.height = height;
        this.width = width;

        rowHolderPanel = new JPanel(new GridLayout(a, b, c, d));

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.add(rowHolderPanel, BorderLayout.PAGE_START);
        rowHolderPanel.setOpaque(false);
        JScrollPane scroll = new JScrollPane(outerPanel);
        JScrollBar bar = scroll.getHorizontalScrollBar();
        outerPanel.setOpaque(false);
        bar.setOpaque(false);
        setBounds(0,u,width,height+10);
        scroll.setBorder(new EmptyBorder(0,l,0,r));
        //  Color color = new Color(225, 100, 225);
        // color = transparentColor(color, 100);
        setOpaque(false);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBounds(0,0,width,height);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return new Dimension(width, height);
    }

    public void addNew(JPanel panel) {
        rowHolderPanel.add(panel);
        rowHolderPanel.revalidate();
        rowHolderPanel.repaint();
    }

}