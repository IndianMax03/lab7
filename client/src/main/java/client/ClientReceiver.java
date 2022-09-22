package client;

import base.City;
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
//    public Optional<Request> removeLower() {
//        System.out.println(RB.getString("askRL"));
//        return Optional.of(new Request("remove_lower", creator.createCity()));
//    }
//
//    public Optional<Request> removeGreater() {
//        System.out.println(RB.getString("askRG"));
//        return Optional.of(new Request("remove_greater", creator.createCity()));
//    }
//

//
//    public Optional<Request> update(String arg) {
//        Long id = Validator.validateId(arg);
//        System.out.println(RB.getString("askUpd"));
//        return Optional.of(new Request("update", arg, creator.createCity()));
//    }
//
//    public Optional<Request> removeById(String arg) {
//        Long id = Validator.validateId(arg);
//        if (id == null) {
//            System.out.println(RB.getString("invalidId"));
//            return Optional.empty();
//        }
//        return Optional.of(new Request("remove_by_id", arg));
//    }
//
//    public Optional<Request> removeAllByGovernment(String arg) {
//        if (Government.asLowerCaseStringList().contains(arg.toLowerCase())) {
//            return Optional.of(new Request("remove_all_by_government", Government.fromString(arg).toString()));
//        }
//        System.out.println(RB.getString("invalidGov"));
//        return Optional.empty();
//    }

}
