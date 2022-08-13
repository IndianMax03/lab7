package command;

public abstract class AbstractCommand<T, R> {

	public abstract R execute(T arg);

	public String getHelp(){
		return "";
	}

}
