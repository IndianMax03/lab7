package client;

import clientLogger.ClientLogger;
import gui.view.AccountView;
import gui.view.AuthView;
import gui.listeners.LoginPasswordListener;
import listening.Request;
import listening.Response;

import javax.swing.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.*;

public class Terminal {

    private final Logger LOGGER = ClientLogger.getLogger();
    private final ResourceBundle RB = ResourceBundle.getBundle("client");

    Scanner scanner;
    private final ClientInvoker clientInvoker;
    private final Client client;
    volatile private String login = "";
    volatile private String password = "";

    private final AuthView authView = new AuthView();

    public Terminal(ClientInvoker clientInvoker, Client client) {
        this.clientInvoker = clientInvoker;
        this.client = client;
    }

    public void startApp() {

    greeting();

    }

    private void authorization(String log, String pas) {
        Request authRequest = new Request("authorization");
        authRequest.setLogin(log);
        authRequest.setPassword(pas);
        client.send(authRequest);
        Optional<Response> optResponse = client.recieve();
        if (optResponse.isPresent()) {
            Response response = optResponse.get();
            if (!response.getMessage().isEmpty()) {
                authView.showMessage(response.getMessage());
            }
            else {
                login = log;
                password = pas;
                authView.hide();
                personalAccount();
            }
        }
    }

    private void greeting() {
        SwingUtilities.invokeLater(() -> {
            authView.show();
            authView.addLPListener(new LoginPasswordListener() {
                @Override
                public void created(String log, String pas) {
                    login = log;
                    password = pas;
                    authorization(log, pas);
                }
            });
        });
    }

    private void personalAccount() {
        SwingUtilities.invokeLater(() -> {
            AccountView accountView = new AccountView(login);
            accountView.show();
        });
    }

}
