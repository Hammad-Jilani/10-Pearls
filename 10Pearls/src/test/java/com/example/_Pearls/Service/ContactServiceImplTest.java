package com.example._Pearls.Service;

import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import com.example._Pearls.Exception.ContactNotFoundException;
import com.example._Pearls.repository.ContactRepository;
import com.example._Pearls.service.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceImplTest {

    @InjectMocks
    private ContactServiceImpl contactService;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateContact() {
        Contact contact = new Contact();
        contact.setEmail("test@example.com");
        contact.setName("John Doe");
        contact.setNumber("123456789");

        User user = new User();
        user.setId(1L);

        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact result = contactService.createContact(contact, user);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void testDeleteContact_ContactNotFound() {
        Long userId = 1L;
        Contact contact = new Contact();
        contact.setContactId(1L);
        contact.setUser(user);

        when(contactRepository.findByContactIdAndUserId(contact.getContactId(), userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> contactService.deleteContact(contact, user));
    }

    @Test
    void testDeleteContact_Success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);  // Ensure the user is properly set up

        Contact contact = new Contact();
        contact.setContactId(1L);
        contact.setUser(user);  // Make sure the contact is associated with the correct user

        // Mock repository behavior
        when(contactRepository.findByContactIdAndUserId(contact.getContactId(), userId))
                .thenReturn(Optional.of(contact));
        doNothing().when(contactRepository).delete(any(Contact.class));

        // Call the method under test
        Contact result = contactService.deleteContact(contact, user);

        // Verify the result
        assertEquals(contact, result);
        verify(contactRepository, times(1)).delete(contact);
    }


    @Test
    void testFindAllContact() {
        Long userId = 1L;
        Contact contact1 = new Contact();
        contact1.setContactId(1L);
        Contact contact2 = new Contact();
        contact2.setContactId(2L);

        List<Contact> contacts = List.of(contact1, contact2);
        when(contactRepository.findAllByUserId(userId)).thenReturn(contacts);

        List<Contact> result = contactService.findAllContact(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(contact1));
        assertTrue(result.contains(contact2));
    }

    @Test
    void testFindPaginatedContacts() {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Contact contact1 = new Contact();
        contact1.setContactId(1L);
        Contact contact2 = new Contact();
        contact2.setContactId(2L);

        Page<Contact> contactsPage = mock(Page.class);
        when(contactsPage.getContent()).thenReturn(List.of(contact1, contact2));
        when(contactRepository.findByUserId(userId, pageable)).thenReturn(contactsPage);

        Page<Contact> result = contactService.findPaginatedContacts(userId, 0, 10);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().contains(contact1));
        assertTrue(result.getContent().contains(contact2));
    }

    @Test
    void testUpdateContact_ContactNotFound() {
        Contact requestContact = new Contact();
        requestContact.setName("Updated Name");

        Contact contact = new Contact();
        contact.setContactId(1L);

        when(contactRepository.findByContactIdAndUserId(contact.getContactId(), user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> contactService.updateContact(requestContact, contact, user));
    }

    @Test
    void testUpdateContact_Success() {
        Contact requestContact = new Contact();
        requestContact.setName("Updated Name");
        requestContact.setEmail("updated@example.com");

        Contact contact = new Contact();
        contact.setContactId(1L);
        contact.setName("Old Name");
        contact.setEmail("old@example.com");

        when(contactRepository.findByContactIdAndUserId(contact.getContactId(), user.getId()))
                .thenReturn(Optional.of(contact));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact result = contactService.updateContact(requestContact, contact, user);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void testFindContact_ContactNotFound() {
        Long contactId = 1L;

        when(contactRepository.findById(contactId)).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> contactService.findContact(contactId));
    }

    @Test
    void testFindContact_Success() {
        Long contactId = 1L;
        Contact contact = new Contact();
        contact.setContactId(contactId);

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

        Contact result = contactService.findContact(contactId);

        assertNotNull(result);
        assertEquals(contactId, result.getContactId());
    }

}
