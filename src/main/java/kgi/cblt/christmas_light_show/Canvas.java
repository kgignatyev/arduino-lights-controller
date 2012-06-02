package kgi.cblt.christmas_light_show;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by
 * User: kgignatyev
 */
public class Canvas extends JPanel {

    public final BufferedImage img;
    LightsState lightsState;
    Thread painterThread;

    LightShape[] lightShapes;

    public Canvas(String imgSourceName, LightShape[] lightShapes, LightsState lightsState) throws IOException {
        img = ImageIO.read(new File(imgSourceName));
        this.lightShapes = lightShapes;
        this.lightsState = lightsState;
        painterThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Canvas.this.repaint();
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        painterThread.start();
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < lightShapes.length; i++) {
            LightShape lightShape = lightShapes[i];
            if (lightIsOn(i, lightsState)) {
                drawLightShape(lightShape, g2);
            }
        }

    }

    private boolean lightIsOn(int i, LightsState lightsState) {
        if (i == 0 && lightsState.L0) return true;
        if (i == 1 && lightsState.L1) return true;
        if (i == 2 && lightsState.L2) return true;
        if (i == 3 && lightsState.L3) return true;
        if (i == 4 && lightsState.L4) return true;
        if (i == 5 && lightsState.L5) return true;
        if (i == 6 && lightsState.L6) return true;
        if (i == 7 && lightsState.L7) return true;
        if (i == 8 && lightsState.L8) return true;
        if (i == 9 && lightsState.L9) return true;
        if (i == 10 && lightsState.L10) return true;
        if (i == 11 && lightsState.L11) return true;
        if (i == 12 && lightsState.L12) return true;
        if (i == 13 && lightsState.L13) return true;
        return false;
    }

    private void drawLightShape(LightShape lightShape, Graphics2D g2) {
        g2.setPaint(getColorByName(lightShape.colorName));
        Stroke st = new BasicStroke(4);
        g2.setStroke(st);
        for (Line line : lightShape.lines) {
            g2.draw(new Line2D.Double(line.start.x, line.start.y, line.end.x, line.end.y));
        }

    }

    private Paint getColorByName(String colorName) {
        if ("R".equalsIgnoreCase(colorName)) {
            return Color.RED;
        } else if ("B".equalsIgnoreCase(colorName)) {
            return Color.BLUE;
        } else if ("G".equalsIgnoreCase(colorName)) {
            return Color.GREEN;
        } else if ("W".equalsIgnoreCase(colorName)) {
            return Color.WHITE;
        } else if ("Y".equalsIgnoreCase(colorName)) {
            return Color.YELLOW;
        }
        return Color.WHITE;
    }


}
