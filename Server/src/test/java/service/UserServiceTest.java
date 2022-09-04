package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService;
    private final String login = "test";
    private final String password = "test";

    @Before
    public void before() {
        userService = new UserService();
        new CityService();
    }

    @After
    public void after() {
        userService.remove(login, password);
    }

    @Test
    public void createWithNewUser() {
        assertTrue(userService.create(login, password));
    }

    @Test
    public void createWithExistingUser() {
        userService.create(login, password);
        assertFalse(userService.create(login, password));
    }

    @Test
    public void createWithEmptyLogin() {
        Boolean result1 = userService.create("", password);
        Boolean result2 = userService.create("     ", password);
        Boolean result3 = userService.create("\t", password);
        Boolean result4 = userService.create("\t\t\t", password);
        Boolean result5 = userService.create("\r", password);
        Boolean result6 = userService.create("\n", password);
        boolean result = result1 || result2 || result3 || result4 || result5 || result6;
        assertFalse(result);
    }

    @Test
    public void createWithEmptyPassword() {
        assertTrue(userService.create(login, "     "));
        userService.remove(login, "     ");
    }

    @Test
    public void checkExistsIfExists() {
        userService.create(login, password);
        assertTrue(userService.checkExists(login, password));
    }

    @Test
    public void checkExistsIfNotExists() {
        assertFalse(userService.checkExists(login, password));
    }

    @Test
    public void checkImpostorIfImpostor() {
        userService.create(login, password);
        assertTrue(userService.checkImpostor(login, "xxx"));
    }

    @Test
    public void checkImpostorIfNotImpostor() {
        userService.create(login, password);
        assertFalse(userService.checkImpostor(login, password));
    }

    @Test
    public void removeIfExists() {
        userService.create(login, password);
        assertTrue(userService.remove(login, password));
    }

    @Test
    public void removeIfNotExists() {
        assertFalse(userService.remove(login, password));
    }
}