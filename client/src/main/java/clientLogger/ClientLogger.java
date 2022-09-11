package clientLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class ClientLogger {
    static {
        try (InputStream is = ClientLogger.class.getClassLoader().getResourceAsStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger LOGGER = Logger.getLogger(ClientLogger.class.getName());

    public static Logger getLogger() {
        return LOGGER;
    }
}
