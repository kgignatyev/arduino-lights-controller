package kgi.cblt.christmas_light_show;

/**
 * Created by
 * User: kgignatyev
 */
public class LightsAutoPlayer extends Thread {

    LightsState lightsState;
    public LightsAutoPlayer(LightsState p) {
        lightsState = p;
    }


    public boolean rollWindows = false;

    public boolean rollLines = false;

    private int[] rollingWindowStates = new int[]{
            1,  2,  4,  8,  //one window
            1,  2,  4,  8,   //one window
            1,  3,  6, 12, 9, //two windows
            1,  3,  6, 12, 9, //two windows
            11, 7, 14, 13,  // one dark window
            11, 7, 14, 13, // one dark window
    };
    private int[] rollingLinesStates = new int[]{1, 2, 4, 8, 4, 2};

    @Override
    public void run() {
        while (true) {

            try {
                if (rollWindows) {
                    setStateToNextRollingWindowsState();
                } else{
                    setWindowsState(0);
                }
                if (rollLines) {
                    setStateToNextRollingLinesState();
                }else{
                    setLinesState(0);
                }
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    int rollingWStateIndex = 0;

    private void setStateToNextRollingWindowsState() {
        rollingWStateIndex = rollingWStateIndex + 1;
        if (rollingWStateIndex >= rollingWindowStates.length) {
            rollingWStateIndex = 0;
        }
        int st = rollingWindowStates[rollingWStateIndex];
        setWindowsState(st);
    }

    private void setWindowsState(int st) {
        lightsState.w6On = (st & 1) > 0;
        lightsState.w4On = (st & 2) > 0;
        lightsState.w5On = (st & 4) > 0;
        lightsState.w7On = (st & 8) > 0;
    }

    int rollingLState = 0;

    private void setStateToNextRollingLinesState() {
        rollingLState = rollingLState + 1;
        if (rollingLState >= rollingLinesStates.length) {
            rollingLState = 0;
        }
        int st = rollingLinesStates[rollingLState];
        setLinesState(st);
    }

    private void setLinesState(int st) {
        lightsState.l1On = (st & 1) > 0;
        lightsState.l3On = (st & 2) > 0;
        lightsState.l4On = (st & 4) > 0;
        lightsState.l2On = (st & 8) > 0;
    }
}
