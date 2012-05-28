package kgi.cblt.christmas_light_show;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: max
 * Date: 12/31/10
 * Time: 2:51 PM
 */
public class WriterThread extends Thread{

    MainPanel panel;

    public WriterThread(MainPanel panel) {
        this.panel = panel;
    }

    long lastWriteTime;
    long loopDuration = 0;
        long nLoops = 0;
        long startTime = 0;
        long endTime;

        public void run() {
            System.out.println("WriterThread started = " + new Date());
            while (true) {

                startTime = System.currentTimeMillis();
//                System.out.println("startTime = " + startTime);
//                if ((startTime - endTime) > 10) {
//                    System.out.println("delay: = " + (startTime - endTime));
//                }
                try {
                    String s = " roofOn:" + panel.roofOn
                            + " treeOn:" + panel.treeOn
                            + " w1:" + panel.w1On
                            + " w2:" + panel.w2On
                            + " w3:" + panel.w3On
                            + " w4:" + panel.w4On
                            + " w5:" + panel.w5On
                            + " w6:" + panel.w6On
                            + " w7:" + panel.w7On
                            + " l1:" + panel.l1On
                            + " l2:" + panel.l2On
                            + " l3:" + panel.l3On
                            + " l4:" + panel.l4On;
                    //f.setTitle(s);

                    Integer controlInt = panel.makeInt();
                    //System.out.println("s = " + controlInt);
                    if ("write".equals(panel.mode)) {

                        if( lastWriteTime > 0 ){
                           panel.lightShowWriter.write("d"+ (System.currentTimeMillis() - lastWriteTime) + "\n");
                        }
                        panel.lightShowWriter.write(String.valueOf(controlInt) + "\n");
                        panel.lightShowWriter.flush();
                        lastWriteTime = System.currentTimeMillis();
                        if (panel.mp3Player.pl.player != null && panel.mp3Player.pl.player.isComplete()) {

                            panel.lightShowWriter.close();
                            System.out.println("loopDuration = " + loopDuration + " " + nLoops);
                            System.out.println(new Date());
                            System.exit(0);
                        }
                    }
                    Thread.sleep(100);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    //break;
                }
                nLoops++;
                endTime = System.currentTimeMillis();
                if (loopDuration == 0) {
                    loopDuration = endTime - startTime;
                } else {
                    loopDuration = (loopDuration + (endTime - startTime)) / 2;
                }
            }
        }

}
