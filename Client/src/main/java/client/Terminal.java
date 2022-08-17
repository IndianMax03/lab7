package client;

import base.User;
import input.Validator;
import listening.Request;
import listening.Response;

import java.util.Scanner;

public class Terminal {

	Scanner scanner;
	private final ClientInvoker clientInvoker;
	private final Client client;
	private String login = "";
	private String password = "";

	public Terminal(ClientInvoker clientInvoker, Client client) {
		this.clientInvoker = clientInvoker;
		this.client = client;
	}

	// todo file executing

	protected void startKeyboard() {

		scanner = new Scanner(System.in);

		System.out.println("Для начала работы вам необходимо авторизоваться в системе.");
		authorization();
		System.out.println("Вы вошли в систему под именем: " + login);

		while (true) {

			System.out.print("Введите команду:\n>");
			String line = scanner.nextLine();

			Request request = lineHandler(line);
			if (request == null)
				continue;
			if (request.getCommandName().equals("authorization")) {
				System.out.println("Пожалуйста, не пытайтесь взломать мою систему.");
				continue;
			}

			request.setLogin(login);
			request.setPassword(password);

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

	private void authorization(){
		System.out.print("Введите логин: ");
		login = scanner.nextLine();
		System.out.print("Введите пароль (можно не вводить): ");
		password = scanner.nextLine();
		Request authRequest = new Request("authorization");
		authRequest.setLogin(login);
		authRequest.setPassword(password);
		client.send(authRequest);
		Response response = client.recieve();
		if (!response.getMessage().isEmpty()){
			System.out.println(response.getMessage());
			authorization();
		}
		return;
	}
}
