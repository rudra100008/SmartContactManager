package com.smartcontactmanager.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
@Repository
public interface ContactDao extends JpaRepository<Contact,Integer>{
   List<Contact> findByUser(User user); 
   //pageable has two information contact per page and current page
   @Query("select c from Contact as c where c.user.id=:userID")
   Page<Contact> findUserByID(@Param("userID")int id,Pageable pageable);
} 
