package com.smartcontactmanager.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.Dao.UserDao;
import com.smartcontactmanager.Entity.User;

@Service
public class UserServices {
    @Autowired
    private UserDao userDao;

    public User saveUser(User user)
    {
       User savedUser= this.userDao.save(user);
       return savedUser;
    }
    public User findUserByUsername(String name)
    {
        return this.userDao.findByUsername(name);
    }
   
}
