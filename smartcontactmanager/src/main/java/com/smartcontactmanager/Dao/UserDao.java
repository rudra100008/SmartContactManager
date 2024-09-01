package com.smartcontactmanager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.Entity.User;

@Repository
public interface UserDao extends JpaRepository<User,Integer>{
    public User findByusername(String username);
} 
