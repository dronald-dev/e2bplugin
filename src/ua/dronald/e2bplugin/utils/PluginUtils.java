package ua.dronald.e2bplugin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class PluginUtils {

    public static String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }


    public static String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String getLogsTime() {
        return "[" + getTime() + "] ";
    }

}
