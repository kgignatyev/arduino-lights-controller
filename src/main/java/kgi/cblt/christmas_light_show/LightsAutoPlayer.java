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


    public boolean prev_roll2_5 = false;
    public boolean roll2_5 = false;

    public boolean prev_roll6_9 = false;
    public boolean roll6_9 = false;

    private int[] roll_2_5_States = new int[]{
            1, 2, 4, 8,  //one window
            1, 2, 4, 8,   //one window
            1, 3, 6, 12, 9, //two windows
            1, 3, 6, 12, 9, //two windows
            11, 7, 14, 13,  // one dark window
            11, 7, 14, 13, // one dark window
    };
    private int[] roll_6_9_States = new int[]{1, 2, 4, 8, 4, 2};

    @Override
    public void run() {
        while (true) {

            try {
                if (roll2_5) {
                    prev_roll2_5 = true;
                    setStateToNext2_5State();
                } else {
                    if (prev_roll2_5) {
                        set2_5State(0);
                        prev_roll2_5 = false;
                    }
                }
                if (roll6_9) {
                    prev_roll6_9 = true;
                    setStateToNext6_9State();
                } else {
                    if (prev_roll6_9) {
                        set6_9State(0);
                        prev_roll6_9 = false;
                    }
                }
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    int rolling2_5stateIndex = 0;

    private void setStateToNext2_5State() {
        rolling2_5stateIndex = rolling2_5stateIndex + 1;
        if (rolling2_5stateIndex >= roll_2_5_States.length) {
            rolling2_5stateIndex = 0;
        }
        int st = roll_2_5_States[rolling2_5stateIndex];
        set2_5State(st);
    }

    private void set2_5State(int st) {
        lightsState.L2 = (st & 1) > 0;
        lightsState.L3 = (st & 2) > 0;
        lightsState.L4 = (st & 4) > 0;
        lightsState.L5 = (st & 8) > 0;
    }

    int rolling6_9State = 0;

    private void setStateToNext6_9State() {
        rolling6_9State = rolling6_9State + 1;
        if (rolling6_9State >= roll_6_9_States.length) {
            rolling6_9State = 0;
        }
        int st = roll_6_9_States[rolling6_9State];
        set6_9State(st);
    }

    private void set6_9State(int st) {
        lightsState.L6 = (st & 1) > 0;
        lightsState.L7 = (st & 2) > 0;
        lightsState.L8 = (st & 4) > 0;
        lightsState.L9 = (st & 8) > 0;
    }
}
