package command;

import java.util.Optional;

public abstract class AbstractCommand<T, R> {

    public abstract Optional<R> execute(T arg);

    public abstract String getHelp();

}
