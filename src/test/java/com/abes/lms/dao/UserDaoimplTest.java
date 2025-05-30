package com.abes.lms.dao;

import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DisplayName("UserDao Implementation Tests")
public class UserDaoimplTest {

    private UserDao userDao;
    private UserDto testUser;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        testUser = new UserDto("testuser", "testpass123");
    }

    @Test
    @DisplayName("Should return all users successfully")
    void testGetAllUsers() {
        List<UserDto> users = userDao.getAllUsers();
        assertNotNull(users, "Users list should not be null");
        // Initially, the list might be empty as no default users are added
        assertTrue(users.size() >= 0, "Users list size should be non-negative");
    }

    @Test
    @DisplayName("Should add user successfully")
    void testAddUser() {
        int initialSize = userDao.getAllUsers().size();
        userDao.addUser(testUser);
        
        List<UserDto> users = userDao.getAllUsers();
        assertEquals(initialSize + 1, users.size(), "User count should increase by 1");
        assertTrue(userDao.isUserExists("testuser"), "Added user should exist");
    }

    @Test
    @DisplayName("Should find user by name successfully")
    void testFindUserByName() throws UserNotFoundException {
        userDao.addUser(testUser);
        
        UserDto found = userDao.findUserByName("testuser");
        assertNotNull(found, "Found user should not be null");
        assertEquals("testuser", found.getName(), "Username should match");
        assertEquals("testpass123", found.getPassword(), "Password should match");
        assertTrue(found.getBorrowedBooks().isEmpty(), "New user should have no borrowed books");
    }

    @Test
    @DisplayName("Should throw exception when finding non-existent user")
    void testFindNonExistentUser() {
        assertThrows(UserNotFoundException.class, () -> {
            userDao.findUserByName("nonexistentuser");
        }, "Should throw UserNotFoundException for non-existent user");
    }

    @Test
    @DisplayName("Should check user existence correctly")
    void testIsUserExists() {
        // Test with non-existing user
        assertFalse(userDao.isUserExists("testuser"), "Should return false for non-existing user");
        
        // Add user and test again
        userDao.addUser(testUser);
        assertTrue(userDao.isUserExists("testuser"), "Should return true for existing user");
        
        // Test case insensitive
        assertTrue(userDao.isUserExists("TESTUSER"), "Should be case insensitive");
    }

    @Test
    @DisplayName("Should authenticate user with correct credentials")
    void testAuthenticateUserSuccess() {
        userDao.addUser(testUser);
        
        boolean authenticated = userDao.authenticateUser("testuser", "testpass123");
        assertTrue(authenticated, "Should authenticate user with correct credentials");
    }

    @Test
    @DisplayName("Should fail authentication with wrong password")
    void testAuthenticateUserWrongPassword() {
        userDao.addUser(testUser);
        
        boolean authenticated = userDao.authenticateUser("testuser", "wrongpassword");
        assertFalse(authenticated, "Should not authenticate user with wrong password");
    }

    @Test
    @DisplayName("Should fail authentication with non-existent user")
    void testAuthenticateNonExistentUser() {
        boolean authenticated = userDao.authenticateUser("nonexistentuser", "anypassword");
        assertFalse(authenticated, "Should not authenticate non-existent user");
    }

    @Test
    @DisplayName("Should handle case insensitive user operations")
    void testCaseInsensitiveOperations() throws UserNotFoundException {
        userDao.addUser(testUser);
        
        // Test case insensitive find
        UserDto found = userDao.findUserByName("TESTUSER");
        assertNotNull(found, "Should find user with different case");
        assertEquals("testuser", found.getName(), "Original case should be preserved");
        
        // Test case insensitive existence check
        assertTrue(userDao.isUserExists("TestUser"), "Should find user with mixed case");
        
        // Test case insensitive authentication
        assertTrue(userDao.authenticateUser("TESTUSER", "testpass123"), 
                  "Should authenticate with different case username");
    }

    @Test
    @DisplayName("Should handle null and empty inputs gracefully")
    void testNullAndEmptyInputs() {
        // Test with null username
        assertFalse(userDao.isUserExists(null), "Should handle null username");
        assertFalse(userDao.authenticateUser(null, "password"), "Should handle null username in auth");
        
        // Test with empty username
        assertFalse(userDao.isUserExists(""), "Should handle empty username");
        assertFalse(userDao.authenticateUser("", "password"), "Should handle empty username in auth");
        
        // Test authentication with null password
        userDao.addUser(testUser);
        assertFalse(userDao.authenticateUser("testuser", null), "Should handle null password");
    }

    @Test
    @DisplayName("Should maintain user data integrity")
    void testUserDataIntegrity() throws UserNotFoundException {
        UserDto user1 = new UserDto("user1", "pass1");
        UserDto user2 = new UserDto("user2", "pass2");
        
        userDao.addUser(user1);
        userDao.addUser(user2);
        
        // Verify both users exist independently
        assertTrue(userDao.isUserExists("user1"), "User1 should exist");
        assertTrue(userDao.isUserExists("user2"), "User2 should exist");
        
        // Verify correct authentication for each
        assertTrue(userDao.authenticateUser("user1", "pass1"), "User1 should authenticate with correct password");
        assertTrue(userDao.authenticateUser("user2", "pass2"), "User2 should authenticate with correct password");
        
        // Verify cross-authentication fails
        assertFalse(userDao.authenticateUser("user1", "pass2"), "User1 should not authenticate with user2's password");
        assertFalse(userDao.authenticateUser("user2", "pass1"), "User2 should not authenticate with user1's password");
    }

    @Test
    @DisplayName("Should handle multiple users with different cases")
    void testMultipleUsersWithDifferentCases() {
        UserDto user1 = new UserDto("john", "password1");
        UserDto user2 = new UserDto("JOHN", "password2");
        
        userDao.addUser(user1);
        userDao.addUser(user2);
        
        List<UserDto> users = userDao.getAllUsers();
        assertEquals(2, users.size(), "Should have 2 users with different cases");
        
        // Both should exist
        assertTrue(userDao.isUserExists("john"), "Lowercase john should exist");
        assertTrue(userDao.isUserExists("JOHN"), "Uppercase JOHN should exist");
    }
}