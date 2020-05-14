package Component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BackgroundPanel extends JPanel {
    private BufferedImage background;

    public BackgroundPanel(String image)
    {
        try {
            background = ImageIO.read(new File(image));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(450, 500);
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
