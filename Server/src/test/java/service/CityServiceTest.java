package service;

import base.*;
import org.junit.*;

import java.util.SortedSet;

public class CityServiceTest {

	private final CityService cityService = new CityService();
	private final UserService userService = new UserService();
	private final City city = new City("name", new Coordinates(1d,1d), 1, 1, 1, Climate.TUNDRA, Government.THEOCRACY, StandardOfLiving.HIGH,
			Human.newHumanByLeader(Leaders.ALINA));

	@Before
	public void before() {
		userService.create("test", "test"); //  adds new user
	}

	@After
	public void after() {
		cityService.clearByUser("test");
		userService.remove("test", "test");
	}

	@Test
	public void testCreateWithValidLogin() {
		int result = cityService.create(city, "test"); //  create new city returning it id
		Assert.assertNotEquals(result, -1);
	}

	@Test
	public void testCreateWithInvalidLogin() {
		int result = cityService.create(city, "xxx"); //  create new city returning it id
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
		Boolean result = cityService.updateById(id, city, "test");
		Assert.assertEquals(result, true);
	}

	@Test
	public void testUpdateByIdWithInvalidId() {
		cityService.create(city, "test");
		city.setName("updateTest");
		Boolean result = cityService.updateById(-1, city, "test");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testUpdateByIdWithFalseLogin() {
		int id = cityService.create(city, "test");
		city.setName("updateTest");
		Boolean result = cityService.updateById(id, city, "xxx");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testRemoveByIdWithValidId() {
		int id = cityService.create(city, "test");
		Boolean result = cityService.removeById(id, "test");
		Assert.assertEquals(result, true);
	}

	@Test
	public void testRemoveByIdWithInvalidId() {
		cityService.create(city, "test");
		Boolean result = cityService.removeById(-1, "test");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testRemoveByIdWithFalseLogin() {
		int id = cityService.create(city, "test");
		Boolean result = cityService.removeById(id, "xxx");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testClearByUserWithValidLogin() {
		cityService.create(city, "test");
		Boolean result = cityService.clearByUser("test");
		Assert.assertEquals(result, true);
	}

	@Test
	public void testClearByUserWithInvalidLogin() {
		cityService.create(city, "test");
		Boolean result = cityService.clearByUser("xxx");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testRemoveAllByGovernmentWithExistingCity() {
		city.setGovernment(Government.ANARCHY);
		cityService.create(city, "test");
		Boolean result = cityService.removeAllByGovernment(Government.ANARCHY.toString(), "test");
		Assert.assertEquals(result, true);
	}

	@Test
	public void testRemoveAllByGovernmentWithNotExistingCity() {
		Boolean result = cityService.removeAllByGovernment(Government.ANARCHY.toString(), "test");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testRemoveAllByGovernmentWithFalseArgument() {
		city.setGovernment(Government.ANARCHY);
		cityService.create(city, "test");
		Boolean result = cityService.removeAllByGovernment(Government.DICTATORSHIP.toString(), "test");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testRemoveAllByGovernmentWithFalseLogin() {
		city.setGovernment(Government.ANARCHY);
		cityService.create(city, "test");
		Boolean result = cityService.removeAllByGovernment(Government.ANARCHY.toString(), "xxx");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testRemoveGreaterWithCompletedCondition() {
		city.setName("xyz");
		cityService.create(city, "test");
		city.setName("abc");
		Boolean result = cityService.removeGreater(city, "test");
		Assert.assertEquals(result, true); //  cause xyz > abc
	}

	@Test
	public void testRemoveGreaterWithNotCompletedCondition() {
		city.setName("abc");
		cityService.create(city, "test");
		city.setName("xyz");
		Boolean result = cityService.removeGreater(city, "test");
		Assert.assertEquals(result, false); //  cause abc < xyz
	}

	@Test
	public void testRemoveGreaterWithFalseLogin() {
		city.setName("abc");
		cityService.create(city, "test");
		city.setName("xyz");
		Boolean result = cityService.removeGreater(city, "xxx");
		Assert.assertEquals(result, false);
	}

	@Test
	public void testRemoveLowerWithCompletedCondition() {
		city.setName("abc");
		cityService.create(city, "test");
		city.setName("xyz");
		Boolean result = cityService.removeLower(city, "test");
		Assert.assertEquals(result, true); //  cause abc < xyz
	}

	@Test
	public void testRemoveLowerWithNotCompletedCondition() {
		city.setName("xyz");
		cityService.create(city, "test");
		city.setName("abc");
		Boolean result = cityService.removeLower(city, "test");
		Assert.assertEquals(result, false); //  cause xyz > abc
	}

	@Test
	public void testRemoveLowerWithFalseLogin() {
		city.setName("abc");
		cityService.create(city, "test");
		city.setName("xyz");
		Boolean result = cityService.removeLower(city, "xxx");
		Assert.assertEquals(result, false);
	}
}