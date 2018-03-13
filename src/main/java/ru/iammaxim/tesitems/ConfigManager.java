package ru.iammaxim.tesitems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by maxim on 03.03.2017.
 */
public class ConfigManager {
    private static final String FILEPATH = "galedwell.cfg";
    private static HashMap<String, String> settings = new HashMap<>();

    public static void loadConfig() throws IOException {
        File f = new File(FILEPATH);
        if (!f.exists()) {
            f.createNewFile();
            writeDefaultFile(f); //this will setup default values and write it to config
        } else {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] KandV = line.split("=");
                set(KandV[0], KandV[1]);
            }
        }
    }

    private static void writeDefaultFile(File f) throws IOException {
        //set default values
        set("enableBlur", "false");
        set("fontScaleFactor", "2.0");

        //write values to file
        FileWriter writer = new FileWriter(f);
        settings.forEach((k, v) -> {
            try {
                writer.write(k + "=" + v + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    public static void set(String key, String value) {
        settings.put(key, value);
    }

    public static String get(String key) {
        return settings.get(key);
    }

    public static boolean getBool(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static float getFloat(String key) {
        return Float.parseFloat(get(key));
    }
}
