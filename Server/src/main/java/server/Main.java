package server;

import listening.Request;
import listening.Response;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final ServerReceiver serverReceiver = new ServerReceiver();
    private static final ServerInvoker serverInvoker = new ServerInvoker(serverReceiver);

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        serverReceiver.initCollection();

        while (true) {
            if (System.in.available() > 0) {
                String servcomment;
                try {
                    servcomment = (new Scanner(System.in)).nextLine();
                } catch (NullPointerException e) {
                    System.exit(0);
                    return;
                }
                if (servcomment.equals("exit")) {
                    System.out.println("Сервер завершает свою работу.");
                    System.exit(0);
                } else {
                    System.out.println("Сервер поддерживает только одну команду: exit.");
                }
            }

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
}
