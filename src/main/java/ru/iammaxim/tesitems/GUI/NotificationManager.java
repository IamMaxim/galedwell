package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maxim on 1/23/17 at 9:44 PM.
 */
public class NotificationManager {
    private static ArrayList<String> notifications = new ArrayList<>();
    private static ArrayList<Long> spawntimes = new ArrayList<>();
    public static final int RENDER_COUNT = 4; //plus one hidden
    public static final long LIVETIME = 4000;

    public static void addNotification(String text) {
        TESItems.getMinecraft().addScheduledTask(() -> {
            spawntimes.add(System.currentTimeMillis() + notifications.size() * LIVETIME);
            notifications.add(text);
        });
    }

    public static void update() {
        Iterator<Long> it = spawntimes.iterator();
        while (it.hasNext()) {
            Long st = it.next();
            if (System.currentTimeMillis() - st > LIVETIME) {
                notifications.remove(spawntimes.indexOf(st));
                it.remove();
            }
        }
    }

    public static float getFirstLivetime() {
        return Math.min((float) (LIVETIME + spawntimes.get(0) - System.currentTimeMillis()) / LIVETIME, 1);
    }

    public static List<String> getNotificationsToRender() {
        return notifications.subList(0, Math.min(RENDER_COUNT, notifications.size()));
    }
}
