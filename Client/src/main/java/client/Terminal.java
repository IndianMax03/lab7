package client;

import commands.ExecuteScript;
import listening.Request;
import listening.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

public class Terminal {

    Scanner scanner;
    private final ClientInvoker clientInvoker;
    private final Client client;
    private String login = "";
    private String password = "";

    public Terminal(ClientInvoker clientInvoker, Client client) {
        this.clientInvoker = clientInvoker;
        this.client = client;
    }

    public String startFile(String filename) {
        System.out.println("Выполняется файл: " + filename);
        String pathToFile = new File(filename).getAbsolutePath();
        File file = new File(pathToFile);
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return "Файл не найден.";
        }
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Optional<Request> optRequest = lineHandler(line);
            if (!optRequest.isPresent()) {
                return "Выполнение execute_script в файле: " + filename
                        + " принудительно прекращено строкой с рекурсивным вызовом: " + line;
            } else {
                Request request = optRequest.get();
                if (request.getCommandName().equals("execute_script")) {
                    System.out.println(startFile(request.getArgument()));
                    continue;
                }
                request.setLogin(login);
                request.setPassword(password);
                client.send(request);
                Optional<Response> optResponse = client.recieve();
                if (!optResponse.isPresent()) {
                    return "На сервер прошла команда execute_script или сервер не ответил. Выполнение команды остановлено.";
                } else {
                    Response response = optResponse.get();
                    if (response.getAnswer() == null) {
                        System.out.println(response.getMessage());
                    } else {
                        for (String ans : response.getAnswer()) {
                            System.out.println(ans);
                        }
                    }
                }
            }
        }
        return "Команда execute_script с файлом " + filename + " завершена.";
    }

    public void startKeyboard() {

        scanner = new Scanner(System.in);
        System.out.println(
                "Для начала работы вам необходимо авторизоваться в системе. В противном случае вы войдёте как гость.");
        System.out.println("Вы хотите авторизоваться? [Да/Нет]");
        while (true) {
            System.out.print(">");
            String ans = scanner.nextLine().trim();
            if (ans.equals("Да")) {
                authorization();
                System.out.println("Вы вошли в систему под именем: " + login);
                break;
            } else if (ans.equals("Нет")) {
                System.out.println("Вы вошли в систему как гость.");
                break;
            }
            System.out.println("[Да/Нет]");
        }

        while (true) {

            System.out.print("Введите команду:\n>");
            String line = scanner.nextLine();

            Optional<Request> optRequest = lineHandler(line);
            if (optRequest.isPresent()) {
                Request request = optRequest.get();
                if (request.getCommandName().equals("execute_script")) {
                    System.out.println(startFile(request.getArgument()));
                    ExecuteScript.clearPaths();
                    continue;
                }
                request.setLogin(login);
                request.setPassword(password);

                client.send(request);
                Optional<Response> optResponse = client.recieve();

                if (!optResponse.isPresent()) {
                    System.exit(-1);
                } else {
                    Response response = optResponse.get();
                    if (response.getAnswer() == null) {
                        System.out.println(response.getMessage());
                    } else {
                        for (String ans : response.getAnswer()) {
                            System.out.println(ans);
                        }
                    }
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
        System.out.print("Введите логин: ");
        login = scanner.nextLine();
        System.out.print("Введите пароль (можно не вводить): ");
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
}
