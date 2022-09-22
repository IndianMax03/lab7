package client;

import base.City;
import base.Government;
import gui.util.DialogFrame;
import listening.Request;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientReceiver {

    private final ResourceBundle RB = ResourceBundle.getBundle("client");
    JFrame frame = DialogFrame.getFrame();
    Font font = DialogFrame.getFont();

    public ClientReceiver() {
    }

    public Optional<Request> add(City city) {
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(new Request("add", city));
    }

    public Optional<Request> exit() {
        frame.setFont(font);
        JOptionPane.showMessageDialog(frame, "Bye", "INFO", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        return Optional.empty();
    }

    public Optional<Request> help() {
        return Optional.of(new Request("help"));
    }

    public Optional<Request> info() {
        return Optional.of(new Request("info"));
    }

    public Optional<Request> clear() {
        return Optional.of(new Request("clear"));
    }

    public Optional<Request> addIfMin(City city) {
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(new Request("add_if_min", city));
    }

    public Optional<Request> filterStartsWithName(String line) {
        return Optional.of(new Request("filter_starts_with_name", line));
    }

    public Optional<Request> removeAllByGovernment(Government government) {
        return Optional.of(new Request("remove_all_by_government", government.toString()));
    }

    public Optional<Request> removeGreater(City city) {
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(new Request("remove_greater", city));
    }

    public Optional<Request> removeLower(City city) {
        if (city == null) {
            return Optional.empty();
        }
        return Optional.of(new Request("remove_lower", city));
    }

    public Optional<Request> removeById(Integer arg) {
        return Optional.of(new Request("remove_by_id", arg.toString()));
    }

    public Optional<Request> update(Integer arg, City city) {
        return Optional.of(new Request("update", arg.toString(), city));
    }





}
