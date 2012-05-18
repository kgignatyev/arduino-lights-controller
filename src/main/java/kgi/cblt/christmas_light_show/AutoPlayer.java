package kgi.cblt.christmas_light_show;

/**
 * Created by IntelliJ IDEA.
 * User: max
 * Date: 12/27/10
 * Time: 3:08 PM
 */
public class AutoPlayer extends Thread {

    public AutoPlayer(MainPanel p) {
        this.panel = p;
    }

    public MainPanel panel;

    public boolean rollingWindows = false;
    public boolean prevRollingWindows = false;
    public boolean rollingLines = false;
    public boolean prevRollingLines = false;
    private int[] rollingWindowStates = new int[]{
            1, 1, 2, 2,4,4,8,8,  //one window
            1, 1, 2, 2,4,4,8,8,  //one window
            1, 1,3,3,6,6,12,12,9,9, //two windows
            1, 1,3,3,6,6,12,12,9,9, //two windows
            11,11,7,7,14,14,13,13, // one dark window
            11,11,7,7,14,14,13,13, // one dark window
    };
    private int[] rollingLinesStates = new int[]{1, 2, 4, 8, 4, 2};

    @Override
    public void run() {
        while (true) {

            try {

                Thread.sleep(150);
                if (rollingWindows) {
                    prevRollingWindows = true;
                    setStateToNextRollingWindowsState();
                }
                if(!rollingWindows && prevRollingWindows ){
                    prevRollingWindows = false;
                    setWindowsState(0);
                }
                if (rollingLines) {
                    prevRollingLines = true;
                    setStateToNextRollingLinesState();
                }
                if( !rollingLines && prevRollingLines){
                    prevRollingLines = false;
                    setLinesState(0);
                }
                 panel.repaint();
                Thread.yield();
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
        panel.w6On = (st & 1) > 0;
        panel.w4On = (st & 2) > 0;
        panel.w5On = (st & 4) > 0;
        panel.w7On = (st & 8) > 0;
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
        panel.l1On = (st & 1) > 0;
        panel.l3On = (st & 2) > 0;
        panel.l4On = (st & 4) > 0;
        panel.l2On = (st & 8) > 0;
    }
}
