package com.smartcontactmanager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.Entity.User;

@Repository

public interface UserDao extends JpaRepository<User,Integer>{
    @Query("select u from User u where u.username=username ")
    public User findByUsername(@Param("username") String username);
} 
