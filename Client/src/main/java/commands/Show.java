package commands;

import listening.Request;

public class Show extends ClientCommand {
	@Override
	public Request execute(String arg) {
		if (arg != null){
			System.out.println("Команда show не принимает аргументы.");
			return null;
		}
		return new Request("show");
	}
}
