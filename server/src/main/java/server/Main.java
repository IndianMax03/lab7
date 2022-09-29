package server;

import listening.Request;
import listening.Response;
import serverLogger.ServerLogger;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    public static Locale locale = Locale.getDefault();
    private static final Logger LOGGER = ServerLogger.getLogger();
    private static final ResourceBundle RB = ResourceBundle.getBundle("server");
    private static final ServerReceiver serverReceiver = new ServerReceiver();
    private static final ServerInvoker serverInvoker = new ServerInvoker(serverReceiver);

    public static void main(String[] args) {

        Server server = new Server();

        serverReceiver.initCollection();
        while (true) {


            if (parseComment() == 0) {
                LOGGER.info(RB.getString("end"));
                return;
            }

            new Thread(() -> {
                Optional<Request> optRequest = server.recieve();
                if (optRequest.isPresent()) {
                    Request request = optRequest.get();
                    new Thread(() -> {
                        if (request.getCommandName().equals("update_table")) {
                            server.send(serverReceiver.getCollection(), request.getClientAddres());
                        } else {
                            Optional<Response> optResponse = serverInvoker.execute(request);
                            if (optResponse.isPresent()) {
                                Response response = optResponse.get();
                                server.send(response, request.getClientAddres());
                            }
                        }
                    }).start();
                }
            }).start();
        }

    }

    /**
     * @return the value 0 if Exception or "exit"
     */
    private static int parseComment() {
        try {
            String comment = "";
            if (System.in.available() > 0) {
                comment = (new Scanner(System.in)).nextLine();
            }
            return comment.compareTo("exit");
        } catch (IOException | NullPointerException e) {
            return 0;
        }
    }
}
