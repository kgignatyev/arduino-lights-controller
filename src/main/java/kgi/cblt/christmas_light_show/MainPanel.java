package kgi.cblt.christmas_light_show;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

/**
 * Created by
 * User: kgignatyev
 */
public class MainPanel extends JPanel implements KeyListener, StateSource {
    boolean roofOn, treeOn, w1On, w2On, w3On, w4On, w5On, w6On, w7On, l1On, l2On, l3On, l4On;
    static JFrame f;

    BufferedImage img = null;
     String mp3fileName;
     String lightShowFileName;
     FileWriter lightShowWriter;
     BufferedReader lightShowReader;
     String mode;
    AutoPlayer autoPlayer;
     MP3Player mp3Player;
     String playMode = null;
     File lightShowFile;
     static Thread writerThread;

    public BufferedReader getLightShowReader() {
        return lightShowReader;
    }

    /**
     * args[0] - write|read
     * args[1] - name of mp3 file
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        try {
            f = new JFrame();
            f.setSize(1600, 600);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            MainPanel mainPanel = new MainPanel(args[0], args[1]);
            if (args.length > 2) {
                mainPanel.playMode = args[2];
            }
            writerThread = new WriterThread(mainPanel);
            writerThread.setDaemon(true);
            writerThread.start();
            f.setContentPane(mainPanel);
            f.setVisible(true);
            System.out.println(new Date());
            mainPanel.autoPlayer = new AutoPlayer(mainPanel);
            mainPanel.autoPlayer.setDaemon(true);
            mainPanel.autoPlayer.start();

            ArduinoCommunicator comm = new ArduinoCommunicator(mainPanel);
            comm.setDaemon(true);
            comm.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.RED);
        Stroke st = new BasicStroke(4);
        g2.setStroke(st);
        if (roofOn) {
            g2.draw(new Line2D.Double(42, 70, 1520, 70));
        }
        if (treeOn) {
            g2.draw(new Line2D.Double(905, 155, 1000, 280));
            g2.draw(new Line2D.Double(1000, 280, 1120, 300));
        }
        if (w1On) {
            g2.draw(new Line2D.Double(140, 85, 480, 85));
            g2.draw(new Line2D.Double(480, 85, 480, 260));
            g2.draw(new Line2D.Double(140, 85, 140, 260));
        }
        if (w2On) {
            g2.draw(new Line2D.Double(140, 380, 480, 380));
            g2.draw(new Line2D.Double(140, 380, 140, 480));
            g2.draw(new Line2D.Double(480, 380, 480, 480));
        }
        if (w3On) {
            g2.draw(new Line2D.Double(590, 100, 785, 100));
            g2.draw(new Line2D.Double(590, 100, 590, 250));
            g2.draw(new Line2D.Double(590, 250, 785, 250));
            g2.draw(new Line2D.Double(785, 100, 785, 250));
        }
        if (w4On) {
            g2.draw(new Line2D.Double(880, 100, 1075, 100));
            g2.draw(new Line2D.Double(880, 100, 880, 195));
            g2.draw(new Line2D.Double(1075, 100, 1075, 195));
        }
        if (w5On) {
            g2.draw(new Line2D.Double(880, 380, 880, 475));
            g2.draw(new Line2D.Double(880, 380, 1075, 380));
            g2.draw(new Line2D.Double(1075, 380, 1075, 475));
        }
        if (w6On) {
            g2.draw(new Line2D.Double(1240, 100, 1440, 100));
            g2.draw(new Line2D.Double(1240, 100, 1240, 190));
            g2.draw(new Line2D.Double(1440, 100, 1440, 190));
        }
        if (w7On) {
            g2.draw(new Line2D.Double(1240, 380, 1440, 380));
            g2.draw(new Line2D.Double(1240, 380, 1240, 475));
            g2.draw(new Line2D.Double(1440, 380, 1440, 475));
        }
        if (l1On) {
            g2.draw(new Line2D.Double(920, 215, 1390, 370));
        }
        if (l2On) {
            g2.draw(new Line2D.Double(920, 370, 1390, 215));
        }
        if (l3On) {
            g2.draw(new Line2D.Double(920, 260, 1390, 325));
        }
        if (l4On) {
            g2.draw(new Line2D.Double(920, 320, 1390, 265));
        }
    }

    public MainPanel(String mode, String mp3fn) throws IOException {
        this.mode = mode;
        this.mp3fileName = mp3fn;
        lightShowFileName = createLightShowNameFrom(mp3fileName);
        lightShowFile = new File(new File(mp3fileName).getParentFile(), lightShowFileName);
        System.out.println("lightShowFileName = " + lightShowFile.getAbsolutePath());
        if ("write".equals(mode)) {
            lightShowWriter = ( new FileWriter(lightShowFile));
        }
        if ("read".equals(mode)) {
            createShowReader();

        }
        setFocusable(true);
        addKeyListener(this);
        try {
            img = ImageIO.read(new File("src/test/home.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(mp3Player = new MP3Player(mp3fileName)).start();

        if ("read".equals(mode)) {
            Thread lsPlayer = new Thread() {

                long  delay = 100;
                @Override
                public void run() {

                    while (true) {
                        try {
                            String line = getLightShowReader().readLine();
                            if (line != null) {
                                int lightsState = Integer.parseInt(line);
                                line = getLightShowReader().readLine();
                                if( line.startsWith("d")){
                                    delay = Long.parseLong( line.substring(1));
                                }
                                intToState(lightsState);
                                MainPanel.this.repaint();

                            } else {
                                createShowReader();
                            }
                            Thread.sleep(delay);
                            if (mp3Player.pl.player != null && mp3Player.pl.player.isComplete()) {
                                if (playMode != null) {
                                    mp3Player.pl.player = null;

                                } else {
                                    System.exit(0);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            };
            lsPlayer.start();
        }
    }

    private void createShowReader() throws IOException {
        if (lightShowReader != null) {
            lightShowReader.close();
        }
        lightShowReader = new BufferedReader(new FileReader(lightShowFile));
    }


    private String createLightShowNameFrom(String mp3fileName) {
        File f = new File(mp3fileName);
        String fn = f.getName();
        return fn.replaceAll(".mp3", ".ls.txt");
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_R:
                roofOn = !roofOn;
                treeOn = roofOn;
                w3On = roofOn;
                break;
            case KeyEvent.VK_E:
                autoPlayer.rollingWindows = !autoPlayer.rollingWindows;
                break;
            case KeyEvent.VK_W:
                autoPlayer.rollingLines = !autoPlayer.rollingLines;
                break;
            case KeyEvent.VK_A:
                w1On = true;
                break;
            case KeyEvent.VK_S:
                w2On = true;
                break;
            case KeyEvent.VK_D:
                roofOn = true;
                treeOn = true;
                w3On = true;
                break;
            case KeyEvent.VK_F:
                w4On = true;
                break;
            case KeyEvent.VK_J:
                w5On = true;
                break;
            case KeyEvent.VK_K:
                w6On = true;
                break;
            case KeyEvent.VK_L:
                w7On = true;
                break;
            case KeyEvent.VK_1:
                l1On = true;
                break;
            case KeyEvent.VK_2:
                l2On = true;
                break;
            case KeyEvent.VK_3:
                l3On = true;
                break;
            case KeyEvent.VK_4:
                l4On = true;
        }
        this.repaint();
    }

    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_R:

                break;
            case KeyEvent.VK_E:

                break;
            case KeyEvent.VK_A:
                w1On = false;
                break;
            case KeyEvent.VK_S:
                w2On = false;
                break;
            case KeyEvent.VK_D:
                roofOn = false;
                treeOn = false;
                w3On = false;
                break;
            case KeyEvent.VK_F:
                w4On = false;
                break;
            case KeyEvent.VK_J:
                w5On = false;
                break;
            case KeyEvent.VK_K:
                w6On = false;
                break;
            case KeyEvent.VK_L:
                w7On = false;
                break;
            case KeyEvent.VK_1:
                l1On = false;
                break;
            case KeyEvent.VK_2:
                l2On = false;
                break;
            case KeyEvent.VK_3:
                l3On = false;
                break;
            case KeyEvent.VK_4:
                l4On = false;
        }
        this.repaint();
    }



    public Integer makeInt() {
        int i = 0;
        if (roofOn) i += 1;
        if (treeOn) i += 2;
        if (w1On) i += 4;
        if (w2On) i += 8;
        if (w3On) i += 8192;
        if (w4On) i += 32;
        if (w5On) i += 64;
        if (w6On) i += 128;
        if (w7On) i += 256;
        if (l1On) i += 512;
        if (l2On) i += 1024;
        if (l3On) i += 2048;
        if (l4On) i += 4096;
        return i;
    }

    public void intToState(int lightsState) {
        roofOn = (lightsState & 1) > 0;
        treeOn = (lightsState & 2) > 0;
        w1On = (lightsState & 4) > 0;
        w2On = (lightsState & 8) > 0;
        w3On = (lightsState & 8192) > 0;
        w4On = (lightsState & 32) > 0;
        w5On = (lightsState & 64) > 0;
        w6On = (lightsState & 128) > 0;
        w7On = (lightsState & 256) > 0;
        l1On = (lightsState & 512) > 0;
        l2On = (lightsState & 1024) > 0;
        l3On = (lightsState & 2048) > 0;
        l4On = (lightsState & 4096) > 0;
    }

    public int getState() {
        return makeInt();
    }
}