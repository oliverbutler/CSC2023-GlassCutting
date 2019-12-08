package com.example.glasscutting;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.glasscutting.Shelf;

/**
 * [Not required, ignore this]
 * 
 * Class to visually output the sheets in a JPanel, badly implemented and not
 * meant for marking, as such no comments will be provided.
 */
class SheetDisplay extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    List<Sheet> sheets;
    Random rand;

    public SheetDisplay(final List<Sheet> sheets) {
        this.sheets = sheets;
        rand = new Random();
    }

    private void doDrawing(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        int shelfYOffset = 0;
        for (final Sheet sheet : sheets) {
            int yOffset = 0;
            g2d.drawRect(0, shelfYOffset, sheet.getWidth(), sheet.getHeight());
            for (final Shelf s : sheet.getShelves()) {
                g2d.drawRect(0, shelfYOffset + sheet.getHeight() - s.getHeight() - yOffset, sheet.getWidth(),
                        s.getHeight());
                int xOffset = 0;
                for (final Shape sp : s.getShapes()) {
                    final float red = rand.nextFloat();
                    final float green = rand.nextFloat();
                    final float blue = rand.nextFloat();
                    g2d.setPaint(new Color(red, green, blue));
                    g2d.fillRect(xOffset, shelfYOffset + sheet.getHeight() - sp.getHeight() - yOffset, sp.getWidth(),
                            sp.getHeight());
                    xOffset += sp.getWidth();
                }
                g2d.setPaint(new Color(0, 0, 0));
                yOffset += s.getHeight();
            }
            shelfYOffset += sheet.getHeight() + 10;
        }
    }

    @Override
    public void paintComponent(final Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}

public class Render extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Render(final List<Sheet> sheets) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                initUI(sheets);
            }
        });
    }

    private void initUI(final List<Sheet> sheets) {
        // JScrollPane jsp = new JScrollPane();
        // jsp.add(new SheetDisplay(sheets));
        // jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(new SheetDisplay(sheets));
        setTitle("Sheets");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}