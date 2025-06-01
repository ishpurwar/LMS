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
     */
    @Override
    public List<UserDto> getAllUsers() {
        return CollectionUtil.getAllUsers();
    }

    /**
     * Adds a new user to the system.
     */
    @Override
    public void addUser(UserDto user) {
        CollectionUtil.addUser(user);
    }

    /**
     * Finds and returns a user by their name.
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
     */
    @Override
    public boolean isUserExists(String name) {
        return CollectionUtil.isUserExists(name);
    }

    /**
     * Authenticates a user by comparing the provided name and password
     * with the stored user information.
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
