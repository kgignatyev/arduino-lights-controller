package kgi.cblt.christmas_light_show;

import javax.swing.*;

/**
 * Created by
 * User: kgignatyev
 */
public class LightShow {

    Canvas canvas;
    LightsAutoPlayer lightsAutoPlayer;
    LightStateController lightStateController;
    LightsState lightsState;
    LightShowMusicPlayer musicPlayer;
    ArduinoCommunicator arduinoCommunicator;

    public void run() throws Exception {
        lightsState = new LightsState();
        JFrame frame = new JFrame();
        frame.setTitle("Light Show");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas("src/test/home.jpg",lightsState);
        frame.setContentPane(canvas);
        frame.setSize(canvas.img.getWidth(), canvas.img.getHeight());
        frame.setVisible(true);
        lightsAutoPlayer = new LightsAutoPlayer(lightsState);
        lightsAutoPlayer.start();
        lightStateController = new LightStateController(lightsState,lightsAutoPlayer);
        frame.addKeyListener(lightStateController);
        musicPlayer = new LightShowMusicPlayer("src/test/4.mp3");
        musicPlayer.start();
        arduinoCommunicator = new ArduinoCommunicator(lightsState);
        arduinoCommunicator.start();
    }

    public static void main(String[] args) {
        LightShow lightShow = new LightShow();
        try {
            lightShow.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
