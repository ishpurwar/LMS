package com.abes.lms.dao;

import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.UserNotFoundException;
import com.abes.lms.util.CollectionUtil;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public List<UserDto> getAllUsers() {
        return CollectionUtil.getAllUsers();
    }

    @Override
    public void addUser(UserDto user) {
        CollectionUtil.addUser(user);
    }

    @Override
    public UserDto findUserByName(String name) throws UserNotFoundException {
        UserDto user = CollectionUtil.findUserByName(name);
        if (user == null) {
            throw new UserNotFoundException("User '" + name + "' not found");
        }
        return user;
    }

    @Override
    public boolean isUserExists(String name) {
        return CollectionUtil.isUserExists(name);
    }

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