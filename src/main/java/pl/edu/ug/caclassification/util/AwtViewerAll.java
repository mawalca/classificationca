package pl.edu.ug.caclassification.util;

import pl.edu.ug.caclassification.simulation.SimResult;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class AwtViewerAll {


    SimResult simResult;

    int width;
    int height;

    int sizeX;
    int sizeY;

    public AwtViewerAll(SimResult result) {

        this.simResult = result;

        sizeX = result.getHiddenImg().length;
        sizeY = result.getHiddenImg()[0].length;

        JFrame frame = new JFrame("Cellular automata");

        width = sizeX;
        height = sizeY;


        JLabel ruleLabel = new JLabel(result.getRule().toString());
        JLabel statsLabel = new JLabel(result.getStatsString());

        frame.getContentPane().add(ruleLabel, BorderLayout.PAGE_START);
        frame.getContentPane().add(statsLabel, BorderLayout.PAGE_END);

        JPanel imgsPanel = new JPanel(new FlowLayout());

        List<JComponent> images = new ArrayList<>();

        images.add(makeImg(result.getImg()));
        images.add(makeImg(result.getHiddenImg()));
        images.add(makeImg(result.getAvgImage()));
        result.getSamples().stream().forEach(sample -> images.add(makeImg(sample)));

        images.stream().forEach(image -> imgsPanel.add(image));

        frame.getContentPane().add(imgsPanel, BorderLayout.LINE_START);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        images.stream().forEach(imageViewer -> imageViewer.repaint());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    private JComponent makeImg(byte[][] dataArray){

        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        JComponent viewer = new JComponent() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, width, height, this);
            }
        };

        viewer.setPreferredSize(new Dimension(width, height));

        int[] imageRgb = ((DataBufferInt) image.getRaster()
                .getDataBuffer()).getData();

        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                imageRgb[i * sizeX + j] = 0x00aaaaaa;

        for (int i = 0; i < dataArray.length; i++)
            for (int j = 0; j < dataArray[0].length; j++)
                imageRgb[i * dataArray[0].length + j] = valToColor(dataArray[i][j]);

        return viewer;
    }

    private int valToColor(byte val) {
        int res = 0;
        if (val == 0) res = 0x00aaaaaa;
        if (val == 1) res = 0xFFFFFFFF;
        return res;
    }

}
