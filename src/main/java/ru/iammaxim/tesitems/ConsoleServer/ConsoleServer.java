package ru.iammaxim.tesitems.ConsoleServer;

import java.net.ServerSocket;

/**
 * Created by maxim on 2/23/17 at 9:03 PM.
 */
public class ConsoleServer {
    public static final int PORT = 25563;

    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
