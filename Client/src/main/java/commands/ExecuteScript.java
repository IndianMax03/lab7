package commands;

import listening.Request;

import java.io.File;
import java.util.ArrayList;

public class ExecuteScript extends ClientCommand {

    private static final ArrayList<File> paths = new ArrayList<>();

    @Override
    public Request execute(String arg) {
        if (arg.isEmpty()) {
            System.out.println("Команда execute_script требует аргуемент - filename.");
            return null;
        }
        File file = new File(new File(arg).getAbsolutePath());
        if (!paths.contains(file)) {
            paths.add(file);
            return new Request("execute_script", arg);
        } else {
            System.out.println("Обнаружена рекурсия.");
            return null;
        }
    }

    public static void clearPaths() {
        paths.clear();
    }
}
