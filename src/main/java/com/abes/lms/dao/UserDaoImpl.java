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

  
}