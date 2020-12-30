package wadge.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class WadgeLogger {
    private static Logger logger;

    private WadgeLogger() {}

    public static void setUp() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        try {
            FileHandler txtFile = new FileHandler("logging.log", true);
            SimpleFormatter txtFormatter = new SimpleFormatter();
            txtFile.setFormatter(txtFormatter);
            logger.addHandler(txtFile);
        } catch (SecurityException | IOException e) {
            logger.log(Level.WARNING, "No logging file has been set.");
        }
    }

    public static Logger getLogger() {
        setUp();
        return logger;
    }
}
