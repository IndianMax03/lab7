package service;

import base.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.*;

import java.util.SortedSet;

public class CityServiceTest {

    private final CityService cityService = new CityService();
    private final UserService userService = new UserService();
    private final City city = new City("name", new Coordinates(1d, 1d), 1, 1, 1, Climate.TUNDRA, Government.THEOCRACY,
            StandardOfLiving.HIGH, Human.newHumanByLeader(Leaders.ALINA));

    @Before
    public void before() {
        userService.create("test", "test"); // adds new user
    }

    @After
    public void after() {
        cityService.clearByUser("test");
        userService.remove("test", "test");
    }

    @Test
    public void testCreateWithValidLogin() {
        int result = cityService.create(city, "test"); // create new city returning it id
        Assert.assertNotEquals(result, -1);
    }

    @Test
    public void testCreateWithInvalidLogin() {
        int result = cityService.create(city, "xxx"); // create new city returning it id
        Assert.assertEquals(result, -1);
    }

    @Test
    public void testReadAll() {
        cityService.create(city, "test");
        SortedSet<City> result = cityService.readAll();
        int size = result.size();
        Assert.assertNotEquals(size, 0);
    }

    @Test
    public void testUpdateByIdWithValidId() {
        int id = cityService.create(city, "test");
        city.setName("updateTest");
        assertTrue(cityService.updateById(id, city, "test"));
    }

    @Test
    public void testUpdateByIdWithInvalidId() {
        cityService.create(city, "test");
        city.setName("updateTest");
        assertFalse(cityService.updateById(-1, city, "test"));
    }

    @Test
    public void testUpdateByIdWithFalseLogin() {
        int id = cityService.create(city, "test");
        city.setName("updateTest");
        assertFalse(cityService.updateById(id, city, "xxx"));
    }

    @Test
    public void testRemoveByIdWithValidId() {
        int id = cityService.create(city, "test");
        assertTrue(cityService.removeById(id, "test"));
    }

    @Test
    public void testRemoveByIdWithInvalidId() {
        cityService.create(city, "test");
        assertFalse(cityService.removeById(-1, "test"));
    }

    @Test
    public void testRemoveByIdWithFalseLogin() {
        int id = cityService.create(city, "test");
        assertFalse(cityService.removeById(id, "xxx"));
    }

    @Test
    public void testClearByUserWithValidLogin() {
        cityService.create(city, "test");
        assertTrue(cityService.clearByUser("test"));
    }

    @Test
    public void testClearByUserWithInvalidLogin() {
        cityService.create(city, "test");
        assertFalse(cityService.clearByUser("xxx"));
    }

    @Test
    public void testRemoveAllByGovernmentWithExistingCity() {
        city.setGovernment(Government.ANARCHY);
        cityService.create(city, "test");
        assertTrue(cityService.removeAllByGovernment(Government.ANARCHY.toString(), "test"));
    }

    @Test
    public void testRemoveAllByGovernmentWithNotExistingCity() {
        assertFalse(cityService.removeAllByGovernment(Government.ANARCHY.toString(), "test"));
    }

    @Test
    public void testRemoveAllByGovernmentWithFalseArgument() {
        city.setGovernment(Government.ANARCHY);
        cityService.create(city, "test");
        assertFalse(cityService.removeAllByGovernment(Government.DICTATORSHIP.toString(), "test"));
    }

    @Test
    public void testRemoveAllByGovernmentWithFalseLogin() {
        city.setGovernment(Government.ANARCHY);
        cityService.create(city, "test");
        assertFalse(cityService.removeAllByGovernment(Government.ANARCHY.toString(), "xxx"));
    }

    @Test
    public void testRemoveGreaterWithCompletedCondition() {
        city.setName("xyz");
        cityService.create(city, "test");
        city.setName("abc");
        assertTrue(cityService.removeGreater(city, "test")); // cause xyz > abc
    }

    @Test
    public void testRemoveGreaterWithNotCompletedCondition() {
        city.setName("abc");
        cityService.create(city, "test");
        city.setName("xyz");
        assertFalse(cityService.removeGreater(city, "test")); // cause abc < xyz
    }

    @Test
    public void testRemoveGreaterWithFalseLogin() {
        city.setName("abc");
        cityService.create(city, "test");
        city.setName("xyz");
        assertFalse(cityService.removeGreater(city, "xxx"));
    }

    @Test
    public void testRemoveLowerWithCompletedCondition() {
        city.setName("abc");
        cityService.create(city, "test");
        city.setName("xyz");
        assertTrue(cityService.removeLower(city, "test")); // cause abc < xyz
    }

    @Test
    public void testRemoveLowerWithNotCompletedCondition() {
        city.setName("xyz");
        cityService.create(city, "test");
        city.setName("abc");
        assertFalse(cityService.removeLower(city, "test")); // cause xyz > abc
    }

    @Test
    public void testRemoveLowerWithFalseLogin() {
        city.setName("abc");
        cityService.create(city, "test");
        city.setName("xyz");
        assertFalse(cityService.removeLower(city, "xxx"));
    }
}