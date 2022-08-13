package commands;

import listening.Request;

public class PrintDescending extends ClientCommand {
	@Override
	public Request execute(String arg) {
		if (arg != null){
			System.out.println("Команда print_descending не принимает аргументы.");
			return null;
		}
		return new Request("print_descending");
	}
}
