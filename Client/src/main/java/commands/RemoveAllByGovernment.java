package commands;

import base.Government;
import client.ClientReceiver;
import listening.Request;

import java.util.List;

public class RemoveAllByGovernment extends ClientCommand {

	private final ClientReceiver clientReceiver;

	public RemoveAllByGovernment(ClientReceiver clientReceiver) {
		this.clientReceiver = clientReceiver;
	}


	@Override
	public Request execute(String arg) {
		if (arg == null){
			System.out.println("Команда remove_all_by_government принимает аргумент government.");
			System.out.println("Повторите ввод команды с одним из нижеперечисленных аргументов (регистр не учитывается):");
			byte i = 1;
			for (Government government : Government.values()){
				System.out.println(i + ") " + government.toString());
			}
			return null;
		}
		return clientReceiver.removeAllByGovernment(arg);
	}
}
