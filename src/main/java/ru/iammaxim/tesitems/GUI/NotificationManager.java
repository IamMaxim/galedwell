package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.TESItems;
import ru.iammaxim.tesitems.Utils.ClientThings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maxim on 1/23/17 at 9:44 PM.
 */
public class NotificationManager {
    private static ArrayList<String> notifications = new ArrayList<>();
    private static ArrayList<Long> spawntimes = new ArrayList<>();
    public static final int RENDER_COUNT = 8; //plus one hidden
    public static final long LIVETIME = 4000;

    public static void addNotification(String text) {
        TESItems.getMinecraft().addScheduledTask(() -> {
            spawntimes.add(System.currentTimeMillis() + notifications.size() * LIVETIME);
            notifications.add(text);
        });
    }

    public static void draw() {
        //update notifications
        Iterator<Long> it = spawntimes.iterator();
        while (it.hasNext()) {
            Long st = it.next();
            if (System.currentTimeMillis() - st > LIVETIME) {
                notifications.remove(spawntimes.indexOf(st));
                it.remove();
            }
        }

        //draw notifications
        List<String> notifications = NotificationManager.getNotificationsToRender();

        if (notifications.size() > 0) {
            int first_alpha = (int) (255 * Math.min(NotificationManager.getFirstLivetime() * 6, 1));
            //fixes reset of alpha 0x00 to 0xFF
            if (first_alpha == 0) first_alpha = 1;
            if (first_alpha == 255) first_alpha = 254;

            float y = ((float) 12 / 255 * first_alpha);

            //draw first string
            ClientThings.fontRenderer.drawString(notifications.get(0), 8, y, 0x00FFFFFF + (first_alpha << 24), false);
            y += 12;

            //draw strings between first and last
            for (int i = 1; i < notifications.size() - 1; i++) {
                ClientThings.fontRenderer.drawString(notifications.get(i), 8, y, 0xFFFFFFFF, false);
                y += 12;
            }

            //draw last string
            if (notifications.size() == NotificationManager.RENDER_COUNT)
                ClientThings.fontRenderer.drawString(notifications.get(notifications.size() - 1), 8, y, 0xFFFFFFFF - (first_alpha << 24), false);
            else if (notifications.size() > 1)
                ClientThings.fontRenderer.drawString(notifications.get(notifications.size() - 1), 8, y, 0xFFFFFFFF, false);
        }
    }

    public static float getFirstLivetime() {
        return Math.min((float) (LIVETIME + spawntimes.get(0) - System.currentTimeMillis()) / LIVETIME, 1);
    }

    public static List<String> getNotificationsToRender() {
        return notifications.subList(0, Math.min(RENDER_COUNT, notifications.size()));
    }
}
