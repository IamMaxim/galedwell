package ru.iammaxim.tesitems.GUI;

/**
 * This interface is used to implement waiting for server response in GUI,
 * e.g. do not response to clicks while server hasn't sent you inventory update message
 */
public interface IGuiUpdatable {

    /**
     * Called from message handler to allow interaction
     */
    void update();

    /**
     * Called from GUI to block interaction
     */
    void unupdate();

    /**
     * @return if GUI is ready to interact with player
     */
    boolean updated();
}
