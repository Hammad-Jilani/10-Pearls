package com.example._Pearls.service;

import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {
    Contact createContact(Contact contact, User user);
    Contact deleteContact(Contact contact,User user);
    List<Contact> findAllContact(Long userId);
    Contact findContact(Long contactId);
    List<Contact> searchContacts(String keyword, User user) throws Exception;
    Page<Contact> findPaginatedContacts(Long userId, int page, int size);
    Contact updateContact(Contact requestContact,Contact contact,User user);
}
