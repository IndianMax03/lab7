package client;

import clientLogger.ClientLogger;
import commands.ExecuteScript;
import listening.Request;
import listening.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.*;

public class Terminal {

    private final Logger LOGGER = ClientLogger.getLogger();

    Scanner scanner;
    private final ClientInvoker clientInvoker;
    private final Client client;
    private String login = "";
    private String password = "";

    public Terminal(ClientInvoker clientInvoker, Client client) {
        this.clientInvoker = clientInvoker;
        this.client = client;
    }

    public void startFile(String filename) {
        System.out.println("Выполняется файл: " + filename);
        setScanner(filename);
        if (scanner == null) {
            System.out.println("Файл не найден");
            return;
        }

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Optional<Request> optRequest = lineHandler(line);
            if (!optRequest.isPresent()) {
                System.out.println("Выполнение execute_script в файле: " + filename
                        + " принудительно прекращено строкой с рекурсивным вызовом: " + line);
                return;
            } else {
                Request request = optRequest.get();
                if (request.getCommandName().equals("execute_script")) {
                    startFile(request.getArgument());
                    continue;
                }
                request.setLogin(login);
                request.setPassword(password);
                client.send(request);
                Optional<Response> optResponse = client.recieve();
                if (!optResponse.isPresent()) {
                    LOGGER.log(Level.SEVERE,
                            "На сервер прошла команда execute_script или сервер не ответил. Выполнение команды остановлено.",
                            new RuntimeException());
                } else {
                    Response response = optResponse.get();
                    responseProcessing(response);
                }
            }
        }
        System.out.println("Команда execute_script с файлом " + filename + " завершена.");
    }

    public void startKeyboard() {

        scanner = new Scanner(System.in);

        greeting();

        while (true) {
            System.out.print("Введите команду:\n>");
            String line = scanner.nextLine();

            Optional<Request> optRequest = lineHandler(line);
            if (optRequest.isPresent()) {

                Request request = optRequest.get();
                if (request.getCommandName().equals("execute_script")) {
                    startFile(request.getArgument());
                    ExecuteScript.clearPaths();
                    scanner = new Scanner(System.in);
                    continue;
                }

                request.setLogin(login);
                request.setPassword(password);
                client.send(request);

                Optional<Response> optResponse = client.recieve();
                if (optResponse.isPresent()) {
                    Response response = optResponse.get();
                    responseProcessing(response);
                }

            }
        }
    }

    private Optional<Request> lineHandler(String line) throws NullPointerException {

        while (line.contains("  ")) {
            line = line.replace("  ", " ");
        }
        String[] commandLine = line.split(" ");
        String command = commandLine[0].trim();
        if (command.equals("authorization")) {
            authorization();
            System.out.println("Вы вошли в систему под именем: " + login);
            return Optional.empty();
        }
        if (commandLine.length == 1) {
            return clientInvoker.check(command, null);
        }
        if (commandLine.length == 2) {
            return clientInvoker.check(command, commandLine[1]);
        }
        return Optional.empty();
    }

    private void authorization() {
        System.out.print("Введите логин:\n>");
        login = scanner.nextLine();
        System.out.print("Введите пароль (можно не вводить):\n>");
        password = scanner.nextLine();
        Request authRequest = new Request("authorization");
        authRequest.setLogin(login);
        authRequest.setPassword(password);
        client.send(authRequest);
        Optional<Response> optResponse = client.recieve();
        if (optResponse.isPresent()) {
            Response response = optResponse.get();
            if (!response.getMessage().isEmpty()) {
                System.out.println(response.getMessage());
                authorization();
            }
        }
    }

    private void greeting() {
        System.out.println(
                "Для начала работы вам необходимо авторизоваться в системе. В противном случае вы войдёте как гость.");
        System.out.println("Вы хотите авторизоваться? [да/нет]");
        while (true) {
            System.out.print(">");
            String ans = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (ans.equals("да")) {
                authorization();
                System.out.println("Вы вошли в систему под именем: " + login);
                return;
            } else if (ans.equals("нет")) {
                System.out.println("Вы вошли в систему как гость.");
                return;
            }
            System.out.println("[да/нет]");
        }
    }

    private void setScanner(String filename) {
        File file = new File(filename).getAbsoluteFile();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ignored) {
            scanner = null;
        }
    }

    private void responseProcessing(Response response) {
        if (response.getAnswer() == null) {
            System.out.println(response.getMessage());
        } else {
            for (String ans : response.getAnswer()) {
                System.out.println(ans);
            }
        }
    }

}
