package pl.edu.ug;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class AwtViewer {

    private final int[] imageRgb;
    private JComponent viewer;

    public AwtViewer(int sizeX, int sizeY) {

        JFrame frame = new JFrame("Cellular Automata");
        final int width = sizeX;
        final int height = sizeY;

        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        viewer = new JComponent() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, width, height, this);
            }
        };

        viewer.setPreferredSize(new Dimension(width, height));
        frame.getContentPane().add(viewer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        imageRgb = ((DataBufferInt) image.getRaster()
                .getDataBuffer()).getData();

        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                imageRgb[i * sizeX + j] = 0x00aaaaaa;


    }

    private int valToColor(byte val) {
        int res = 0;
        if (val == 0) res = 0x00aaaaaa;
        if (val == 1) res = 0xFFFFFFFF;
        return res;
    }


    public void drawArray(byte[][] array) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[0].length; j++)
                imageRgb[i * array[0].length + j] = valToColor(array[i][j]);

        viewer.repaint();
    }
}
