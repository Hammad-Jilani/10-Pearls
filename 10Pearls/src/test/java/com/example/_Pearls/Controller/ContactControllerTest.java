package com.example._Pearls.Controller;

import com.example._Pearls.Controller.ContactController;
import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import com.example._Pearls.response.MessageResponse;
import com.example._Pearls.service.ContactServiceImpl;
import com.example._Pearls.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContactControllerTest {

    @Mock
    private ContactServiceImpl contactService;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private ContactController contactController;

    private User user;
    private Contact contact;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock user
        user = new User();
        user.setId(1L);
        user.setEmail("testuser@example.com");

        // Setup mock contact
        contact = new Contact();
        contact.setContactId(1L);
        contact.setName("John Doe");
        contact.setEmail("johndoe@example.com");
        contact.setNumber("1234567890");
    }

    @Test
    void testGetContact_Success() throws Exception {
        // Mock the service methods
        when(userService.findUserProfileByJwt(anyString())).thenReturn(user);
        when(userService.findAllContactsByUserId(user.getId())).thenReturn(List.of(contact));

        // Call the controller method
        ResponseEntity<List<Contact>> response = contactController.getContact("Bearer someJwt");

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(contact, response.getBody().get(0));

        // Verify interactions with mock services
        verify(userService, times(1)).findUserProfileByJwt(anyString());
        verify(userService, times(1)).findAllContactsByUserId(user.getId());
    }
    @Test
    void testGetPaginatedContacts_Success() throws Exception {
        // Mock the service methods
        Page<Contact> pageContacts = mock(Page.class);
        when(userService.findUserProfileByJwt(anyString())).thenReturn(user);
        when(contactService.findPaginatedContacts(user.getId(), 0, 5)).thenReturn(pageContacts);

        // Call the controller method
        ResponseEntity<Page<Contact>> response = contactController.getPaginatedContacts("Bearer someJwt", 0, 5);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Verify interactions with mock services
        verify(userService, times(1)).findUserProfileByJwt(anyString());
        verify(contactService, times(1)).findPaginatedContacts(user.getId(), 0, 5);
    }

    @Test
    void testCreateContact_Success() throws Exception {
        // Mock the service methods
        when(userService.findUserProfileByJwt(anyString())).thenReturn(user);
        when(contactService.createContact(any(Contact.class), eq(user))).thenReturn(contact);

        // Create the request contact
        Contact requestContact = new Contact();
        requestContact.setName("Jane Doe");
        requestContact.setEmail("janedoe@example.com");
        requestContact.setNumber("1122334455");

        // Call the controller method
        ResponseEntity<MessageResponse> response = contactController.createContact(requestContact, "Bearer someJwt");

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Contact Created", response.getBody().getMessage());

        // Verify interactions with mock services
        verify(contactService, times(1)).createContact(any(Contact.class), eq(user));
    }


    @Test
    void testDeleteContact_Success() throws Exception {
        // Mock the service methods
        when(userService.findUserProfileByJwt(anyString())).thenReturn(user);
        when(contactService.findContact(contact.getContactId())).thenReturn(contact);
        when(contactService.deleteContact(contact, user)).thenReturn(contact);

        // Call the controller method
        ResponseEntity<MessageResponse> response = contactController.deleteContact(contact.getContactId(), "Bearer someJwt");

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Contact Deleted successfully", response.getBody().getMessage());

        // Verify interactions with mock services
        verify(contactService, times(1)).findContact(contact.getContactId());
        verify(contactService, times(1)).deleteContact(contact, user);
    }


    @Test
    void testUpdateContact_Success() throws Exception {
        // Mock the service methods
        when(userService.findUserProfileByJwt(anyString())).thenReturn(user);
        when(contactService.findContact(contact.getContactId())).thenReturn(contact);
        when(contactService.updateContact(any(Contact.class), eq(contact), eq(user))).thenReturn(contact);

        // Create the request
        Contact requestContact = new Contact();
        requestContact.setName("Updated Name");
        requestContact.setEmail("updated@example.com");
        requestContact.setNumber("9876543210");

        // Call the controller method
        ResponseEntity<MessageResponse> response = contactController.updateContact(contact.getContactId(), requestContact, "Bearer someJwt");

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Contact updated successfully", response.getBody().getMessage());

        // Verify interactions with mock services
        verify(contactService, times(1)).findContact(contact.getContactId());
        verify(contactService, times(1)).updateContact(any(Contact.class), eq(contact), eq(user));
    }

}
