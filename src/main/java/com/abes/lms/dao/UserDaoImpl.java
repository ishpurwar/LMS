package com.abes.lms.dao;

import java.util.List;

import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.UserNotFoundException;
import com.abes.lms.util.CollectionUtil;

/**
 * Implementation of the UserDao interface for managing user operations.
 * Utilizes CollectionUtil for performing user-related data handling.
 */
public class UserDaoImpl implements UserDao {

    /**
     * Retrieves the list of all users in the system.
     *
     * @return List of UserDto objects
     */
    @Override
    public List<UserDto> getAllUsers() {
        return CollectionUtil.getAllUsers();
    }

    /**
     * Adds a new user to the system.
     *
     * @param user The UserDto object representing the user to be added
     */
    @Override
    public void addUser(UserDto user) {
        CollectionUtil.addUser(user);
    }

    /**
     * Finds and returns a user by their name.
     *
     * @param name The name of the user to find
     * @return The matching UserDto object
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public UserDto findUserByName(String name) throws UserNotFoundException {
        UserDto user = CollectionUtil.findUserByName(name);
        if (user == null) {
            throw new UserNotFoundException("User '" + name + "' not found");
        }
        return user;
    }

    /**
     * Checks if a user with the given name exists in the system.
     *
     * @param name The name of the user
     * @return true if the user exists; false otherwise
     */
    @Override
    public boolean isUserExists(String name) {
        return CollectionUtil.isUserExists(name);
    }

    /**
     * Authenticates a user by comparing the provided name and password
     * with the stored user information.
     *
     * @param name The name of the user
     * @param password The password to authenticate
     * @return true if authentication is successful; false otherwise
     */
    @Override
    public boolean authenticateUser(String name, String password) {
        try {
            UserDto user = findUserByName(name);
            return user.getPassword().equals(password);
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}
