package com.abes.lms.dao;

import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class UserDaoimplTest {

	private UserDao userDao;
	private UserDto testUser;

	@BeforeEach
	void setUp() {
		userDao = new UserDaoImpl();
		testUser = new UserDto("testuser", "testpass123");
	}

	@Test
	void testGetAllUsers() {
		List<UserDto> users = userDao.getAllUsers();
		assertNotNull(users);
		assertTrue(users.size() >= 0);
	}

	@Test
	void testAddUser() {
		int initialSize = userDao.getAllUsers().size();
		userDao.addUser(testUser);
		assertEquals(initialSize + 1, userDao.getAllUsers().size());
		assertTrue(userDao.isUserExists("testuser"));
	}

	@Test
	void testFindUserByName() throws UserNotFoundException {
		UserDto findUser = new UserDto("finduser", "findpass");
		userDao.addUser(findUser);
		UserDto found = userDao.findUserByName("finduser");
		assertNotNull(found);
		assertEquals("finduser", found.getName());
		assertEquals("findpass", found.getPassword());
	}

	@Test
	void testFindNonExistentUser() {
		try {
			userDao.findUserByName("nonexistentuser");
			fail("Should throw UserNotFoundException");
		} catch (UserNotFoundException e) {
			assertNotNull(e.getMessage());
			assertTrue(e.getMessage().contains("User not found") || e.getMessage().contains("nonexistentuser"));
		}
	}

	@Test
	void testIsUserExists() {
		// Use a unique username to avoid conflicts with other tests
		UserDto uniqueUser = new UserDto("uniqueuser123", "password");
		assertFalse(userDao.isUserExists("uniqueuser123"));
		userDao.addUser(uniqueUser);
		assertTrue(userDao.isUserExists("uniqueuser123"));
	}

	@Test
	void testAuthenticateUser() {
		UserDto authUser = new UserDto("authuser", "authpass");
		userDao.addUser(authUser);
		assertTrue(userDao.authenticateUser("authuser", "authpass"));
		assertFalse(userDao.authenticateUser("authuser", "wrongpassword"));
		assertFalse(userDao.authenticateUser("nonexistentuser", "anypassword"));
	}

	@Test
	void testNullInputs() {
		assertFalse(userDao.isUserExists(null));
		assertFalse(userDao.authenticateUser(null, "password"));
		assertFalse(userDao.authenticateUser("testuser", null));
	}

	@Test
	void testUserDataIntegrity() {
		UserDto user1 = new UserDto("user1", "pass1");
		UserDto user2 = new UserDto("user2", "pass2");

		userDao.addUser(user1);
		userDao.addUser(user2);

		assertTrue(userDao.isUserExists("user1"));
		assertTrue(userDao.isUserExists("user2"));
		assertTrue(userDao.authenticateUser("user1", "pass1"));
		assertTrue(userDao.authenticateUser("user2", "pass2"));
		assertFalse(userDao.authenticateUser("user1", "pass2"));
		assertFalse(userDao.authenticateUser("user2", "pass1"));
	}
}