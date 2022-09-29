package client;

import base.City;
import gui.listeners.TableCellsListener;
import gui.view.AccountView;
import gui.view.AuthView;
import gui.listeners.LoginPasswordListener;
import listening.Request;
import listening.Response;

import javax.swing.*;
import java.util.Optional;

public class Terminal {

    private final Client client;
    volatile private String login = "";
    volatile private String password = "";

    private final AuthView authView = new AuthView();
    private AccountView accountView;

    public Terminal(Client client) {
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
                authView.showErrorMessage(response.getMessage());
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
            accountView = new AccountView(login, password, client);
            accountView.show();
            accountView.getCitiesTable().addTableCellsListener(new TableCellsListener() {
                @Override
                public void created(City city) {
                    Request request = new Request("update", city.getId().toString(), city);
                    request.setLogin(login);
                    request.setPassword(password);
                    client.send(request);
                    Optional<Response> optionalResponse= client.recieve();
                    optionalResponse.ifPresent(response -> accountView.parseAnswer(response));
                }
            });
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(4000);
                        Request request = new Request("update_table");
                        client.send(request);
                        Optional<Response> optionalResponse = client.recieve();
                        if (optionalResponse.isPresent()) {
                            Response tmpRes = optionalResponse.get();
                            if (tmpRes.getCollection() != null) {
                                accountView.updateTable(tmpRes.getCollection());
                            }
                        }
                    } catch (InterruptedException ignore) {
                    }
                }
            }).start();
        });
    }

}
