package com.smartcontactmanager.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import com.smartcontactmanager.Dao.ContactDao;
import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
@Service
public class ContactServices {
    @Autowired
    private ContactDao contactDao;

    public Contact saveContact(Contact contact,User user)
    {
        if (contactDao.existsByContactnameAndUser(contact.getContactname(), user)) {
            throw new IllegalArgumentException("Contact name is already in use");
        }
        if (contactDao.existsByEmailAndUser(contact.getEmail(), user)) {
            throw new IllegalArgumentException("Email is already in use");
        }
        if (contactDao.existsByPhonenumberAndUser(contact.getPhonenumber(), user)) {
            throw new IllegalArgumentException("Phone number  is already in use");
        }
        contact.setUser(user);
        return contactDao.save(contact);
    }

    public List<Contact> getContactsByUser(User user) {
    return contactDao.findByUser(user);  
    }
    public void deleteByID(int id)
    {
        this.contactDao.deleteById(id);;
    }
    public Contact findByIdContact(int id)
    {
        return this.contactDao.findById(id).orElse(null);
    }
    public void updateContact(Contact contact,User user)
    {
        if (contactDao.existsByContactnameAndUser(contact.getContactname(), user)) {
            throw new IllegalArgumentException("Contact name is already in use.");
        }
        if (contactDao.existsByEmailAndUser(contact.getEmail(), user)) {
            throw new IllegalArgumentException("Email already in user");            
        }
        if (contactDao.existsByPhonenumberAndUser(contact.getPhonenumber(), user)) {
            throw new IllegalArgumentException("Phone number is already in use");
        }
        this.contactDao.save(contact);
    }
    public Page<Contact> getContactsByID(int id,Pageable pageable)
    {
        return contactDao.findUserByID(id,pageable);
    }
    public List<Contact> searchItemContacts(String searchItem)
    {
        return this.contactDao.searchContact(searchItem);
    }
    
}
