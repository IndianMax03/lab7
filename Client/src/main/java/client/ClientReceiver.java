package client;

import base.Government;
import input.Creator;
import input.Validator;
import listening.Request;

public class ClientReceiver {

	public Request removeLower(){
		System.out.println("Создайте элемент. Из базы данных будут удалены все ваши элементы, меньшие, чем заданный.");
		return new Request("remove_lower", Creator.createCity());
	}

	public Request removeGreater(){
		System.out.println("Создайте элемент. Из базы данных будут удалены все ваши элементы, превышающие заданный.");
		return new Request("remove_greater", Creator.createCity());
	}

	public Request addIfMin(){
		System.out.println("Создайте элемент. Если его значение меньше, чем у наименьшего элемента в базе данных, то он будет добавлен.");
		return new Request("add_if_min", Creator.createCity());
	}

	public Request add(){
		return new Request("add", Creator.createCity());
	}

	public Request update(String arg){ //  todo
		Long id = Validator.validateId(arg);
		System.out.println("Создайте город, который заменит город с указанным id (если существует и у вас есть права на изменение.");
		return new Request("update", arg, Creator.createCity());
	}

	public Request removeById(String arg){
		Long id = Validator.validateId(arg);
		if (id == null){
			System.out.println("Аргумент id передан неверно.");
			System.out.println("id не может быть отрицательным числом или нулём.");
		}
		return new Request("remove_by_id", arg);
	}

	public Request removeAllByGovernment(String arg){
		if (Government.asLowerCaseStringList().contains(arg.toLowerCase())) {
			return new Request("remove_all_by_government", Government.fromString(arg).toString());
		}
		System.out.println("Передан неверный аргумент government.");
		return null;
	}
}
