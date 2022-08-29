package commands;

import listening.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class ExecuteScript extends ClientCommand {

    private static final ArrayList<File> paths = new ArrayList<>();

    @Override
    public Optional<Request> execute(String arg) {
        if (arg.isEmpty()) {
            System.out.println("Команда execute_script требует аргуемент - filename.");
            return Optional.empty();
        }
        File file = new File(new File(arg).getAbsolutePath());
        if (!paths.contains(file)) {
            paths.add(file);
            return Optional.of(new Request("execute_script", arg));
        } else {
            System.out.println("Обнаружена рекурсия.");
            return Optional.empty();
        }
    }

    public static void clearPaths() {
        paths.clear();
    }
}
