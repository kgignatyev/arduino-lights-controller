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

    public Canvas(String imgSource, LightsState lightsState) throws IOException {
        img = ImageIO.read(new File(imgSource));
        this.lightsState = lightsState;
        painterThread = new Thread(){
            @Override
            public void run() {
                while(true){
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
        g2.setPaint(Color.RED);
        Stroke st = new BasicStroke(4);
        g2.setStroke(st);
        if (lightsState.roofOn) {
            g2.draw(new Line2D.Double(42, 70, 1520, 70));
        }
        if (lightsState.treeOn) {
            g2.draw(new Line2D.Double(905, 155, 1000, 280));
            g2.draw(new Line2D.Double(1000, 280, 1120, 300));
        }
        if (lightsState.w1On) {
            g2.draw(new Line2D.Double(140, 85, 480, 85));
            g2.draw(new Line2D.Double(480, 85, 480, 260));
            g2.draw(new Line2D.Double(140, 85, 140, 260));
        }
        if (lightsState.w2On) {
            g2.draw(new Line2D.Double(140, 380, 480, 380));
            g2.draw(new Line2D.Double(140, 380, 140, 480));
            g2.draw(new Line2D.Double(480, 380, 480, 480));
        }
        if (lightsState.w3On) {
            g2.draw(new Line2D.Double(590, 100, 785, 100));
            g2.draw(new Line2D.Double(590, 100, 590, 250));
            g2.draw(new Line2D.Double(590, 250, 785, 250));
            g2.draw(new Line2D.Double(785, 100, 785, 250));
        }
        if (lightsState.w4On) {
            g2.draw(new Line2D.Double(880, 100, 1075, 100));
            g2.draw(new Line2D.Double(880, 100, 880, 195));
            g2.draw(new Line2D.Double(1075, 100, 1075, 195));
        }
        if (lightsState.w5On) {
            g2.draw(new Line2D.Double(880, 380, 880, 475));
            g2.draw(new Line2D.Double(880, 380, 1075, 380));
            g2.draw(new Line2D.Double(1075, 380, 1075, 475));
        }
        if (lightsState.w6On) {
            g2.draw(new Line2D.Double(1240, 100, 1440, 100));
            g2.draw(new Line2D.Double(1240, 100, 1240, 190));
            g2.draw(new Line2D.Double(1440, 100, 1440, 190));
        }
        if (lightsState.w7On) {
            g2.draw(new Line2D.Double(1240, 380, 1440, 380));
            g2.draw(new Line2D.Double(1240, 380, 1240, 475));
            g2.draw(new Line2D.Double(1440, 380, 1440, 475));
        }
        if (lightsState.l1On) {
            g2.draw(new Line2D.Double(920, 215, 1390, 370));
        }
        if (lightsState.l2On) {
            g2.draw(new Line2D.Double(920, 370, 1390, 215));
        }
        if (lightsState.l3On) {
            g2.draw(new Line2D.Double(920, 260, 1390, 325));
        }
        if (lightsState.l4On) {
            g2.draw(new Line2D.Double(920, 320, 1390, 265));
        }
    }




}
