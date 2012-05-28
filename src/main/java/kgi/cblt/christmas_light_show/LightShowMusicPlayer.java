package kgi.cblt.christmas_light_show;

/**
 * Created by
 * User: kgignatyev
 */
public class LightShowMusicPlayer extends Thread {

    private MP3Player.MP3 player;

    public LightShowMusicPlayer(String mp3FileName) {
        player = new MP3Player.MP3(mp3FileName);
    }

    @Override
    public void run() {
        player.play();
    }
}
