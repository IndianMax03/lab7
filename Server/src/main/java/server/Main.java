package server;

import listening.Request;
import listening.Response;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {

	private static final ServerReceiver serverReceiver = new ServerReceiver();
	private static final ServerInvoker serverInvoker = new ServerInvoker(serverReceiver);

	public static void main(String[] args) throws IOException{

		Server server = new Server();

		try {
			serverReceiver.initCollection();
			while (true) {
				if (System.in.available() > 0) {
					String servcomment;
					try {
						servcomment = (new Scanner(System.in)).nextLine();
					} catch (NullPointerException e) {
						serverReceiver.dropConnection();
						System.exit(0);
						return;
					}
					if (servcomment.equals("exit")) {
						System.out.println("Сервер завершает свою работу.");
						serverReceiver.dropConnection();
						System.exit(0);
					} else {
						System.out.println("Сервер поддерживает только одну команду: exit.");
					}
				}

				new Thread(() -> {
					Request request = server.recieve();
					if (request == null) {
						return;
					}
					new Thread(() -> {
						Response response = serverInvoker.execute(request);
						server.send(response, request.getClientAddres());
					}).start();
				}).start();

			}
		} finally {
			serverReceiver.dropConnection();
		}
	}
}
