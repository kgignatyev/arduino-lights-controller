package kgi.cblt.christmas_light_show;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: max
 * Date: 12/11/10
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class HousePainter extends JPanel{

    BufferedImage img = null;
    public HousePainter( ) {

        try {
            img = ImageIO.read(new File("src/test/home.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(600,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HousePainter jPanel = new HousePainter();
        f.setContentPane(jPanel);


        f.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
        Graphics2D g2 = (Graphics2D) g;

        g2.setPaint(Color.RED);

        Stroke st = new BasicStroke(4);
        g2.setStroke( st );
        g2.draw(new Line2D.Double(30, 300, 200, 30));
    }

}
