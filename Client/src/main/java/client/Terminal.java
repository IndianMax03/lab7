package client;

import listening.Request;
import listening.Response;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Terminal {

	Scanner scanner;
	private final ClientInvoker clientInvoker;
	private final Client client;
	private String output;

	public Terminal(ClientInvoker clientInvoker, Client client) {
		this.clientInvoker = clientInvoker;
		this.client = client;
	}

	// todo file executing

	protected void startKeyboard() {

		scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Введите команду:\n>");
			String line = scanner.nextLine();

			Request request = lineHandler(line);
			if (request == null)
				continue;

			client.send(request);
			Response response = client.recieve();

			if (response == null){
				System.exit(-1);
			}

			if (response.getAnswer() == null){
				System.out.println(response.getMessage());
			} else {
				for (String ans : response.getAnswer()) {
					System.out.println(ans);
				}
			}

		}
	}

	private Request lineHandler(String line) throws NullPointerException {

		while (line.contains("  ")){
			line = line.replace("  ", " ");
		}
		String[] commandLine = line.split(" ");
		String command = commandLine[0].trim();
		Request request = null;
		if (commandLine.length == 1) {
			return clientInvoker.check(command, null);
		}
		if (commandLine.length == 2) {
			return clientInvoker.check(command, commandLine[1]);
		}
		return request;
	}
}
