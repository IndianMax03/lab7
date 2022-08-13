package commands;

import listening.Request;

public class Exit extends ClientCommand {
	@Override
	public Request execute(String arg) {
		if (arg != null){
			System.out.println("Команда exit не принимает аргументы.");
			return null;
		}
		System.out.println("Спасибо за работу. До свидания.");
		System.exit(0);
		return null;
	}
}
