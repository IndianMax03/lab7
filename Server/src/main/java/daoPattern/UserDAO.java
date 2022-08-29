package daoPattern;

public interface UserDAO {

    boolean create(String login, String password);

    boolean check(String login, String password);

    boolean remove(String login, String password);

}
