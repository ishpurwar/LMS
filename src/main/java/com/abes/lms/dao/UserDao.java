package com.abes.lms.dao;

import com.abes.lms.dto.UserDto;
import com.abes.lms.exception.UserNotFoundException;
import java.util.List;

public interface UserDao {
    List<UserDto> getAllUsers();
    void addUser(UserDto user);
    UserDto findUserByName(String name) throws UserNotFoundException;
    boolean isUserExists(String name);
    boolean authenticateUser(String name, String password);
}

