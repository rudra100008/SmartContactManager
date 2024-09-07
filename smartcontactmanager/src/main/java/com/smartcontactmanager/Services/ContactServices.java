package com.smartcontactmanager.Services;


import org.springframework.beans.factory.annotation.Autowired;
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
    
}
