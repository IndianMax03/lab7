package gui.listeners;

import listening.Request;

import java.util.Optional;

public interface CommandListener {

	void created(Optional<Request> request);

}
