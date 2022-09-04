package clientLogger;

import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class ClientLogger {
    static {
        String path = ClientLogger.class.getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);
    }

    private static final Logger LOGGER = Logger.getLogger(ClientLogger.class.getName());

    public static Logger getLogger() {
        return LOGGER;
    }
}
