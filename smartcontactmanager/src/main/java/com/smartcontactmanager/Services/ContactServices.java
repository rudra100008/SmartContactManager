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

    public Contact saveContacts(Contact contact)
    {
        Contact saveContact =contactDao.save(contact);
        return saveContact;
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
    public void updateContact(Contact contact)
    {
        this.contactDao.save(contact);
    }
    public Page<Contact> getContactsByID(int id,Pageable pageable)
    {
        return contactDao.findUserByID(id,pageable);
    }
    
}
