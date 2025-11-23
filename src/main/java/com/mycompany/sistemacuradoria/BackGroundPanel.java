package com.mycompany.sistemacuradoria;

import java.awt.*;
import javax.swing.*;

public class BackGroundPanel extends JPanel {

    private Image bg;

    public BackGroundPanel() {
        bg = new ImageIcon("src/assets/bg_mountains.png").getImage();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        g2d.drawImage(bg, 0, 0, w, h, this);

        GradientPaint gp = new GradientPaint(
            0, 0, new Color(43, 29, 74, 160),
            w, h, new Color(250, 208, 229, 100)
        );

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
