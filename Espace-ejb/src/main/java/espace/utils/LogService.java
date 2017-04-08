package espace.utils;


import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Map;

public class LogService {

    public static void log(LogLevel level, String clazz, String message) {
        Logger logger = Logger.getLogger(clazz);

        switch (level) {
            case TRACE:
                logger.trace(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
        }

    }

    public static String mapToString(Map<String, Object> map, String enclosingDto) {
        StringBuilder builder = new StringBuilder();
        String delim = "";
        builder.append(enclosingDto + " [");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                builder.append(delim);
                builder.append(key + "=");
                if (value instanceof Calendar) {
                    builder.append(((Calendar) value).getTime().toString());
                } else {
                    builder.append(value);
                }
                delim = ", ";
            }
        }
        builder.append("]");
        return builder.toString();
    }


}
