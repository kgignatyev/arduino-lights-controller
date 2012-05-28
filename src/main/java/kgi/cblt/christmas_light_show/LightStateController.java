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

    public LightStateController(LightsState lightState,LightsAutoPlayer autoPlayer) {
        this.lightState = lightState;
        this.autoPlayer = autoPlayer;
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_R:
                lightState.roofOn = !lightState.roofOn;
                lightState.treeOn = lightState.roofOn;
                lightState.w3On = lightState.roofOn;
                break;
            case KeyEvent.VK_E:
                autoPlayer.rollWindows = !autoPlayer.rollWindows;
                break;
            case KeyEvent.VK_W:
                autoPlayer.rollLines = !autoPlayer.rollLines;
                break;
            case KeyEvent.VK_A:
                lightState.w1On = true;
                break;
            case KeyEvent.VK_S:
                lightState.w2On = true;
                break;
            case KeyEvent.VK_D:
                lightState.roofOn = true;
                lightState.treeOn = true;
                lightState.w3On = true;
                break;
            case KeyEvent.VK_F:
                lightState.w4On = true;
                break;
            case KeyEvent.VK_J:
                lightState.w5On = true;
                break;
            case KeyEvent.VK_K:
                lightState.w6On = true;
                break;
            case KeyEvent.VK_L:
                lightState.w7On = true;
                break;
            case KeyEvent.VK_1:
                lightState.l1On = true;
                break;
            case KeyEvent.VK_2:
                lightState.l2On = true;
                break;
            case KeyEvent.VK_3:
                lightState.l3On = true;
                break;
            case KeyEvent.VK_4:
                lightState.l4On = true;
        }

    }

    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_R:

                break;
            case KeyEvent.VK_E:

                break;
            case KeyEvent.VK_A:
                lightState.w1On = false;
                break;
            case KeyEvent.VK_S:
                lightState.w2On = false;
                break;
            case KeyEvent.VK_D:
                lightState.roofOn = false;
                lightState.treeOn = false;
                lightState.w3On = false;
                break;
            case KeyEvent.VK_F:
                lightState.w4On = false;
                break;
            case KeyEvent.VK_J:
                lightState.w5On = false;
                break;
            case KeyEvent.VK_K:
                lightState.w6On = false;
                break;
            case KeyEvent.VK_L:
                lightState.w7On = false;
                break;
            case KeyEvent.VK_1:
                lightState.l1On = false;
                break;
            case KeyEvent.VK_2:
                lightState.l2On = false;
                break;
            case KeyEvent.VK_3:
                lightState.l3On = false;
                break;
            case KeyEvent.VK_4:
                lightState.l4On = false;
        }
    }


}
