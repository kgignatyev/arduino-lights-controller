package kgi.cblt.christmas_light_show;

import javazoom.jl.player.Player;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;


/**
 * Hello world!
 */
public class MP3Player implements Runnable{

    String fileName;
    MP3 pl;

    public MP3Player(String fileName) {
        this.fileName = fileName;
    }

    final static Logger logger = Logger.getLogger(MP3Player.class);

    public static void main(String[] args) {
        MP3Player pl = new MP3Player( "src/test/4.mp3");
        pl.run();
    }

    public void run() {
        pl = new MP3( fileName );
        pl.play();
    }


    public static class MP3 {
    public String filename;
    public Player player;

    // constructor that takes the name of an MP3 file
    public MP3(String filename) {
        this.filename = filename;
    }

    public void close() { if (player != null) player.close(); }

    // play the MP3 file to the sound card
    public void play() {
        try {
            FileInputStream fis     = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();




    }

}
}
