package com.smartcontactmanager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
@Repository
public interface ContactDao extends JpaRepository<Contact,Integer>{
   List<Contact> findByUser(User user);    
} 
