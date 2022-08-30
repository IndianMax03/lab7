package daoPattern;

public interface UserDAO {

    boolean create(String login, String password);

    boolean checkExists(String login, String password);

    boolean checkImpostor(String login, String password);

    boolean remove(String login, String password);

}
