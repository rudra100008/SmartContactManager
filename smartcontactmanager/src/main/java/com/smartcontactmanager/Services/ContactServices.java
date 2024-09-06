package com.smartcontactmanager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.Dao.ContactDao;
import com.smartcontactmanager.Entity.Contact;
@Service
public class ContactServices {
    @Autowired
    private ContactDao contactDao;

    public Contact saveContacts(Contact contact)
    {
        Contact saveContact =contactDao.save(contact);
        return saveContact;
    }
    
}
