package client;

import base.Government;
import input.Creator;
import input.Typer;
import input.Validator;
import listening.Request;

import java.util.Optional;

public class ClientReceiver {

    private final Creator creator;

    public ClientReceiver() {
        this.creator = new Creator(new Typer());
    }

    public Optional<Request> removeLower() {
        System.out.println("Создайте элемент. Из базы данных будут удалены все ваши элементы, меньшие, чем заданный.");
        return Optional.of(new Request("remove_lower", creator.createCity()));
    }

    public Optional<Request> removeGreater() {
        System.out.println("Создайте элемент. Из базы данных будут удалены все ваши элементы, превышающие заданный.");
        return Optional.of(new Request("remove_greater", creator.createCity()));
    }

    public Optional<Request> addIfMin() {
        System.out.println(
                "Создайте элемент. Если его значение меньше, чем у наименьшего элемента в базе данных, то он будет добавлен.");
        return Optional.of(new Request("add_if_min", creator.createCity()));
    }

    public Optional<Request> add() {
        return Optional.of(new Request("add", creator.createCity()));
    }

    public Optional<Request> update(String arg) {
        Long id = Validator.validateId(arg);
        System.out.println(
                "Создайте город, который заменит город с указанным id (если существует и у вас есть права на изменение).");
        return Optional.of(new Request("update", arg, creator.createCity()));
    }

    public Optional<Request> removeById(String arg) {
        Long id = Validator.validateId(arg);
        if (id == null) {
            System.out.println("Аргумент id передан неверно.");
            System.out.println("id не может быть отрицательным числом или нулём.");
            return Optional.empty();
        }
        return Optional.of(new Request("remove_by_id", arg));
    }

    public Optional<Request> removeAllByGovernment(String arg) {
        if (Government.asLowerCaseStringList().contains(arg.toLowerCase())) {
            return Optional.of(new Request("remove_all_by_government", Government.fromString(arg).toString()));
        }
        System.out.println("Передан неверный аргумент government.");
        return Optional.empty();
    }

}
