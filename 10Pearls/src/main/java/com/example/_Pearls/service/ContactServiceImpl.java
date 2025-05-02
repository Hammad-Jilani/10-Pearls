package com.example._Pearls.service;

import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import com.example._Pearls.Exception.ContactNotFoundException;
import com.example._Pearls.repository.ContactRepository;
import com.example._Pearls.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService{

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);


    @Autowired
    private ContactRepository contactRepository;


    @Override
    public Contact createContact(Contact contact, User user) {
        logger.info("Creating contact for user ID: {}", user.getId());

        Contact createContact = new Contact();
        createContact.setEmail(contact.getEmail());
        createContact.setName(contact.getName());
        createContact.setNumber(contact.getNumber());
        createContact.setUser(user);

        Contact saved = contactRepository.save(createContact);
        logger.info("Contact created with ID: {}", saved.getContactId());
        return createContact;
    }

    @Override
    public Contact deleteContact(Contact contact, User user) {
        logger.info("Deleting contact ID: {} for user ID: {}", contact.getContactId(), user.getId());

        Optional<Contact> optionalContact = contactRepository.findByContactIdAndUserId(contact.getContactId(),user.getId());
        if(optionalContact.isEmpty()){
            logger.warn("Attempt to delete non-existing or unauthorized contact ID: {}", contact.getContactId());
            throw new RuntimeException("Contact not found or doesn't belong to user");
        }
        else {
            contactRepository.delete(optionalContact.get());
            logger.info("Deleted contact ID: {}", contact.getContactId());
            return optionalContact.get();
        }
    }

    @Override
    public List<Contact> findAllContact(Long userId) {
        logger.info("Fetching all contacts for user ID: {}", userId);
        return contactRepository.findAllByUserId(userId);
    }
    @Override
    public Page<Contact> findPaginatedContacts(Long userId, int page, int size) {
        logger.info("Fetching paginated contacts for user ID: {}, Page: {}, Size: {}", userId, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return contactRepository.findByUserId(userId, pageable);
    }

    @Override
    public Contact updateContact(Contact requestContact,Contact contact, User user) {

        logger.info("Updating contact ID: {} for user ID: {}", contact.getContactId(), user.getId());

        Optional<Contact> existing = contactRepository.findByContactIdAndUserId(
                contact.getContactId(), user.getId());

        if (existing.isPresent()) {
            Contact existingContact = existing.get();
            existingContact.setName(requestContact.getName());
            existingContact.setNumber(requestContact.getNumber());
            existingContact.setEmail(requestContact.getEmail());
            logger.info("Updated contact ID: {}", contact.getContactId());
            return contactRepository.save(existingContact);
        } else {
            logger.warn("Contact not found or unauthorized update attempt for contact ID: {}", contact.getContactId());
            throw new RuntimeException("Contact not found or does not belong to user");
        }
    }

    public Contact findContactForDetails(Long contactId,Long user) {
        logger.info("Finding contact with Contact ID: {} and User ID : {}", contactId,user);
        Optional<Contact> optionalContact = contactRepository.findByContactIdAndUserId(contactId,user);
        if (optionalContact.isEmpty()){
            logger.warn("Contact not found with Contact ID: {} and User ID : {}", contactId,user);
            throw new ContactNotFoundException("Contact not found with contact Id "+contactId);
        }
        return optionalContact.get();
    }

    @Override
    public Contact findContact(Long contactId) {
        logger.info("Finding contact with ID: {}", contactId);
        Optional<Contact> optionalContact = contactRepository.findById(contactId);
        if (optionalContact.isEmpty()){
            logger.warn("Contact not found with ID: {}", contactId);
            throw new ContactNotFoundException("Contact not found with contact Id "+contactId);
        }
        return optionalContact.get();
    }

    @Override
    public List<Contact> searchContacts(String keyword, User user) throws Exception {
        logger.info("Searching contacts by keyword: '{}' for user ID: {}", keyword, user.getId());
        List<Contact> list = contactRepository
                .findByNameContainingAndUserId(keyword,user.getId());
        return list;
    }
}
