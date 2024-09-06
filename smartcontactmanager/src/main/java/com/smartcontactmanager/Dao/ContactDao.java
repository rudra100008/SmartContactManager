package com.smartcontactmanager.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.Entity.Contact;
@Repository
public interface ContactDao extends JpaRepository<Contact,Integer>{

    
} 
