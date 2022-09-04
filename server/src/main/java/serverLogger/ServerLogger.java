package serverLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerLogger {
    static {
        try (InputStream is = ServerLogger.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger LOGGER = Logger.getLogger(ServerLogger.class.getName());

    public static Logger getLogger() {
        return LOGGER;
    }

}
