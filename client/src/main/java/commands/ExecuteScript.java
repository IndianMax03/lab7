package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ExecuteScript extends ClientCommand {

    private static final Set<String> paths = new HashSet<>();

    public ExecuteScript(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg.isEmpty()) {
            System.out.println(CommandsEnum.EXECUTE_SCRIPT.title + ": " + RB.getString("needArg"));
            return Optional.empty();
        }
        String path = new File(arg).getAbsolutePath();
        if (paths.add(path)) {
            return Optional.of(new Request("execute_script", arg));
        } else {
            return Optional.empty();
        }
    }

    public static void clearPaths() {
        paths.clear();
    }
}
