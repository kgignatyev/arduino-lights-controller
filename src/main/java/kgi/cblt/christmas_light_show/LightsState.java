package kgi.cblt.christmas_light_show;

/**
 * Created by
 * User: kgignatyev
 */
public class LightsState implements StateSource {
    public boolean L0, L1, L2, L3, L4, L5, L6, L7, L8, L9, L10, L11, L12, L13;

    public int getState() {
        int i = 0;
        if (L0) i += 1;
        if (L1) i += 2;
        if (L2) i += 4;
        if (L3) i += 8;
        if (L4) i += 16;
        if (L5) i += 32;
        if (L6) i += 64;
        if (L7) i += 128;
        if (L8) i += 256;
        if (L9) i += 512;
        if (L10) i += 1024;
        if (L11) i += 2048;
        if (L12) i += 4096;
        if (L13) i += 8192;
        return i;
    }
}
