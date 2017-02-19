package ru.iammaxim.tesitems.Utils;

/**
 * Created by maxim on 2/11/17 at 11:03 PM.
 */
public class IDGen {
    private int nextID = -1;

    public int genID() {
        return ++nextID;
    }

    public void update(int id) {
        if (id > nextID)
            nextID = id;
    }
}
