package com.test.vue.vuetest.utils.logs;

import android.util.Log;

/**
 * 
 * Logs all the type of information. This class will help you to send the log
 * information to back end and to store locally in the sdcard, can turn of the
 * flags if you don't want to save the logs.
 * 
 */
public class Logging {
    
    public static void e(String tag, String message, boolean emergencyFlag,boolean classLevelLogFlag) {
        if(!classLevelLogFlag) return;
        String ERROR = "Error";
        if (LoggingConstants.sTurnOnLogsPrint) {
            // write in LogCat.
            Log.e(tag, message);
        }
        saveMessage(tag, message, LoggingKeys.ERROR, emergencyFlag, ERROR);
    }
    
    public static void i(String tag, String message, boolean emergencyFlag,boolean classLevelLogFlag) {
        if(!classLevelLogFlag) return;
        String INFO = "Info";
        if (LoggingConstants.sTurnOnLogsPrint) {
            Log.i(tag, message);
        }
        saveMessage(tag, message, LoggingKeys.INFO, emergencyFlag, INFO);
    }
    
    public static void w(String tag, String message, boolean emergencyFlag,boolean classLevelLogFlag) {
        if(!classLevelLogFlag) return;
        String WARNING = "Warning";
        if (LoggingConstants.sTurnOnLogsPrint) {
            Log.w(tag, message);
        }
        saveMessage(tag, message, LoggingKeys.WARNING, emergencyFlag, WARNING);
    }
    
    public static void d(String tag, String message, boolean emergencyFlag,boolean classLevelLogFlag) {
        if(!classLevelLogFlag) return;
        String DEBUG = "Debug";
        if (LoggingConstants.sTurnOnLogsPrint) {
            Log.d(tag, message);
        }
        saveMessage(tag, message, LoggingKeys.DEBUG, emergencyFlag, DEBUG);
    }
    
    public static void a(String tag, String message, boolean emergencyFlag,boolean classLevelLogFlag) {
        if(!classLevelLogFlag) return;
        String ANALYTICS = "Analytics";
        if (LoggingConstants.sTurnOnLogsPrint) {
            Log.i(tag, message);
        }
        saveMessage(tag, message, LoggingKeys.ANALYTICS, emergencyFlag,
                ANALYTICS);
    }
    
    private static void saveMessage(String tag, String message,
            LoggingKeys messageKey, boolean emergencyFlag,
            String sdCardSubFolder) {
        sendToNetwork(tag, message, messageKey, emergencyFlag);
        writeToSdcard(tag, message, messageKey, sdCardSubFolder);
    }
    
    /**
     * save the log message in the sdcard folder.
     */
    private static void writeToSdcard(final String tag, String message,
            LoggingKeys messageKey, String subFolder) {
        message = getFormatedMessage(message, tag, messageKey);
        if (LoggingConstants.sTurnOnSdCardLogs) {
            SendLogInfo.writeToSdcard(subFolder, message);
        }
    }
    
    /**
     * send the logs to the network
     */
    private static void sendToNetwork(final String tag, String message,
            LoggingKeys messageKey, boolean emergencyFlag) {
        message = getFormatedMessage(message, tag, messageKey);
        if (emergencyFlag) {
            // if true send message immediately to server.
            if (!LoggingConstants.sTurnOnNetworkLogs) {
                SendLogInfo.sendToNetwork(tag, message);
            }
        } else {
            // if flag is false save message locally until it reaches
            // max count.
            LogStore.getInstance().addLogToList(message);
            if (LogStore.getInstance().getLogsCount() > LoggingConstants.MAX_STORED_LOGS_COUNT) {
                SendLogInfo.flush();
            }
        }
    }
    
    private static String getFormatedMessage(String message, String tag,
            LoggingKeys messageKey) {
        message = "#" + messageKey + ": " + tag + message;
        return message;
        
    }
}