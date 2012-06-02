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

    public void run(String imgFileName, String lightShapesFileName, String musicFileName) throws Exception {
        lightsState = new LightsState();
        JFrame frame = new JFrame();
        frame.setTitle("Light Show");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas(imgFileName, LightShapesReader.readShapes(lightShapesFileName), lightsState);
        frame.setContentPane(canvas);
        frame.setSize(canvas.img.getWidth(), canvas.img.getHeight());
        frame.setVisible(true);
        lightsAutoPlayer = new LightsAutoPlayer(lightsState);
        lightsAutoPlayer.start();
        lightStateController = new LightStateController(lightsState, lightsAutoPlayer);
        frame.addKeyListener(lightStateController);
        if (musicFileName != null) {
            musicPlayer = new LightShowMusicPlayer(musicFileName);
            musicPlayer.start();
        }
        arduinoCommunicator = new ArduinoCommunicator(lightsState);
        arduinoCommunicator.start();
    }

    public static void main(String[] args) {
        LightShow lightShow = new LightShow();
        try {
            String audioTrack = (args.length > 2) ? args[2] : null;
            lightShow.run(args[0], args[1], audioTrack);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
