package server;

import listening.Request;
import listening.Response;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final ServerReceiver serverReceiver = new ServerReceiver();
    private static final ServerInvoker serverInvoker = new ServerInvoker(serverReceiver);

    public static void main(String[] args) {

        Server server = new Server();

        serverReceiver.initCollection();

        while (true) {

            if (parseComment() == 0)
                return;

            new Thread(() -> {
                Optional<Request> optRequest = server.recieve();
                if (optRequest.isPresent()) {
                    Request request = optRequest.get();
                    new Thread(() -> {
                        Optional<Response> optResponse = serverInvoker.execute(request);
                        if (optResponse.isPresent()) {
                            Response response = optResponse.get();
                            server.send(response, request.getClientAddres());
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
