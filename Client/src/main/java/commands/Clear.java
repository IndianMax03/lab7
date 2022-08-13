package commands;

import listening.Request;

public class Clear extends ClientCommand{
	@Override
	public Request execute(String arg) {
		if (arg != null){
			System.out.println("Команда clear не принимает аргументы.");
			return null;
		}
		return new Request("clear");
	}

}
