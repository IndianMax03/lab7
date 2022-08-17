package server;

import listening.Request;
import listening.Response;

import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {
	private static final ServerReceiver serverReceiver = new ServerReceiver();
	private static final ServerInvoker serverInvoker = new ServerInvoker(serverReceiver);
	public static void main(String[] args) throws IOException{

		Server server = new Server();
		/*try { todo
			WorkWithFile.fillTheCollection(receiver.getCollection());
		} catch (CsvValidationException e) {
			System.out.println("Ошибка валидации csv файла со входной коллекцией.");
		} catch (FileNotFoundException ex) {
			System.out.println("Файл с исходной коллекцией не найден.");
		}*/

		try {
			serverReceiver.initCollection();
			while (true) {

				/*if (System.in.available() > 0) {
					String servcomment;
					try {
						servcomment = (new Scanner(System.in)).nextLine();
					} catch (NullPointerException e) {
						System.exit(0);
						return;
					}
					if (servcomment.equals("save")) {
						Request request = new Request("save");
						serverInvoker.getCommandMap().get("save").execute(request);
						System.out.println("Коллекция записана в файл.");
					} else if (servcomment.equals("exit")) {
						System.out.println("Сервер завершает свою работу.");
						System.exit(0);
					} else {
						System.out.println("Сервер поддерживает только две команды: save и exit.");
					}
				}*/

				Request request = server.recieve();
				if (request == null) {
					continue;
				}
				Response response = serverInvoker.execute(request);
				server.send(response, request.getClientAddres());
			}
		} catch (NoSuchElementException ex){
			System.exit(0);
		}
	}
}
