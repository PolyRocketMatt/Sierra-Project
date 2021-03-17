package com.github.itheos.sierra;

import com.github.itheos.sierra.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by PolyRocketMatt on 11/03/2021.
 *
 * The logger that will be used by Unreal and
 * it's engine.
 */

public class SierraLogger {

    private Logger logger;
    private Level level;
    private FileHandler handler;
    private Date date;
    private SimpleDateFormat format;

    private boolean initialized, out;

    public SierraLogger() {
        this.date = new Date();
        this.format = new SimpleDateFormat("yyMMddHHmmss");

        String timeStamp = format.format(date);
        File logFile = new File(IOUtils.getLogPath(), "log_" + timeStamp + ".log");

        try {
            if (!logFile.exists())
                logFile.createNewFile();

            this.logger = Logger.getLogger("Unreal Logger");
            this.level = Level.INFO;
            this.handler = new FileHandler(IOUtils.getLogPath() + "log_" + timeStamp + ".log", true);

            for (Handler handler : logger.getParent().getHandlers())
                logger.getParent().removeHandler(handler);

            this.logger.setLevel(level);
            this.logger.addHandler(handler);
            this.handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });

            initialized = true;
            out = true;
        }
        catch (IOException ex) { initialized = false; }
    }

    public void close() {
        log("Closing handler, messages will no longer be logged");
        handler.close();
    }

    public void log(String message) {
        if (initialized) {
            if (out)
                System.out.println(Sierra.getConsolePrefix() + message);
            logger.log(level, format.format(date) + " " + Sierra.getConsolePrefix() + message);
        } else
            System.out.println(Sierra.getConsolePrefix() + message);
    }

    public void log(String... messages) {
        Arrays.stream(messages).forEach(this::log);
    }

}
