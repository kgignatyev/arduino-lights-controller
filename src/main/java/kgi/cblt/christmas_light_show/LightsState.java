package kgi.cblt.christmas_light_show;

/**
 * Created by
 * User: kgignatyev
 */
public class LightsState implements StateSource {
    public boolean roofOn, treeOn, w1On, w2On, w3On, w4On, w5On, w6On, w7On, l1On, l2On, l3On, l4On;

    public int getState() {
        int i = 0;
        if (roofOn) i += 1;
        if (treeOn) i += 2;
        if (w1On) i += 4;
        if (w2On) i += 8;
        if (w3On) i += 8192;
        if (w4On) i += 32;
        if (w5On) i += 64;
        if (w6On) i += 128;
        if (w7On) i += 256;
        if (l1On) i += 512;
        if (l2On) i += 1024;
        if (l3On) i += 2048;
        if (l4On) i += 4096;
        return i;
    }
}
