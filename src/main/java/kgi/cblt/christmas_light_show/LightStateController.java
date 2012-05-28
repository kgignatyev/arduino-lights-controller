package kgi.cblt.christmas_light_show;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by
 * User: kgignatyev
 */
public class LightStateController implements KeyListener {

    LightsState lightState;
    LightsAutoPlayer autoPlayer;

    public LightStateController(LightsState lightState, LightsAutoPlayer autoPlayer) {
        this.lightState = lightState;
        this.autoPlayer = autoPlayer;
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_E:
                autoPlayer.roll2_5 = !autoPlayer.roll2_5;
                break;
            case KeyEvent.VK_W:
                autoPlayer.roll6_9 = !autoPlayer.roll6_9;
                break;
            case KeyEvent.VK_A:
                lightState.L2 = true;
                break;
            case KeyEvent.VK_S:
                lightState.L3 = true;
                break;
            case KeyEvent.VK_D:
                lightState.L4 = true;
                break;
            case KeyEvent.VK_F:
                lightState.L5 = true;
                break;
            case KeyEvent.VK_J:
                lightState.L6 = true;
                break;
            case KeyEvent.VK_K:
                lightState.L7 = true;
                break;
            case KeyEvent.VK_L:
                lightState.L8 = true;
                break;
            case KeyEvent.VK_SEMICOLON:
                lightState.L9 = true;
                break;
            case KeyEvent.VK_U:
                lightState.L10 = true;
                break;
            case KeyEvent.VK_I:
                lightState.L11 = true;
                break;
            case KeyEvent.VK_O:
                lightState.L12 = true;
            case KeyEvent.VK_P:
                lightState.L13 = true;
        }

    }

    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_A:
                lightState.L2 = false;
                break;
            case KeyEvent.VK_S:
                lightState.L3 = false;
                break;
            case KeyEvent.VK_D:
                lightState.L4 = false;
                break;
            case KeyEvent.VK_F:
                lightState.L5 = false;
                break;
            case KeyEvent.VK_J:
                lightState.L6 = false;
                break;
            case KeyEvent.VK_K:
                lightState.L7 = false;
                break;
            case KeyEvent.VK_L:
                lightState.L8 = false;
                break;
            case KeyEvent.VK_SEMICOLON:
                lightState.L9 = false;
                break;
            case KeyEvent.VK_U:
                lightState.L10 = false;
                break;
            case KeyEvent.VK_I:
                lightState.L11 = false;
                break;
            case KeyEvent.VK_O:
                lightState.L12 = false;
            case KeyEvent.VK_P:
                lightState.L13 = false;
        }
    }


}
