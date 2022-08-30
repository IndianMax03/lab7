package command;

public enum CommandsEnum {
	ADD("add"), ADD_IF_MIN("add_if_min"), CLEAR("clear"), EXECUTE_SCRIPT("execute_script"), EXIT("exit"),
	FILTER_STARTS_WITH_NAME("filter_starts_with_name"), HELP("help"), INFO("info"),
	PRINT_DESCENDING("print_descending"), REMOVE_ALL_BY_GOVERNMENT("remove_all_by_government"),
	REMOVE_BY_ID("remove_by_id"), REMOVE_GREATER("remove_greater"), REMOVE_LOWER("remove_lower"), SHOW("show"),
	UPDATE("update"), AUTHORIZATION("authorization");

	public String title;

	CommandsEnum(String name) {
		this.title = name;
	}
}
