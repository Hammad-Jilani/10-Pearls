package com.example._Pearls.Controller;

import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import com.example._Pearls.response.MessageResponse;
import com.example._Pearls.service.ContactServiceImpl;
import com.example._Pearls.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactServiceImpl contactService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<Contact>> getContact
            (@RequestHeader("Authorization") String jwt) throws Exception {
        logger.info("GET /api/contact - Retrieving all contacts for JWT {}",jwt);
        User user = userService.findUserProfileByJwt(jwt);
        List<Contact> list = userService.findAllContactsByUserId(user.getId());
        logger.info("Retrieved {} contacts for user ID: {}", list.size(), user.getId());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/contacts")
    public ResponseEntity<Page<Contact>> getPaginatedContacts(@RequestHeader("Authorization") String jwt,
                                                              @RequestParam int page,
                                                              @RequestParam int size) throws Exception {
        logger.info("GET /api/contact/contacts - page: {}, size: {}, JWT: {}", page, size, jwt);
        User user = userService.findUserProfileByJwt(jwt);
        Page<Contact> contacts = contactService.findPaginatedContacts(user.getId(), page, size);
        logger.info("Paginated contacts retrieved for user ID: {}", user.getId());
        return ResponseEntity.ok(contacts);
    }

    @PutMapping("/update/{contactId}")
    public ResponseEntity<MessageResponse> updateContact(
            @PathVariable Long contactId,
            @RequestBody Contact requestContact
            ,@RequestHeader("Authorization") String jwt) throws Exception {
        logger.info("PUT /api/contact/update/{} - Updating contact with data: {}", contactId, requestContact);
        Contact contact = contactService.findContact(contactId);
        User user = userService.findUserProfileByJwt(jwt);
        Contact update = contactService.updateContact(requestContact,contact,user);
        MessageResponse messageResponse = new MessageResponse("Contact updated successfully");
        logger.info("Contact with ID {} updated for user ID: {}", contactId, user.getId());
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<MessageResponse> deleteContact(
            @PathVariable Long contactId
            ,@RequestHeader("Authorization") String jwt) throws Exception {
        logger.info("DELETE /api/contact/{} - Deleting contact", contactId);
        Contact contact = contactService.findContact(contactId);
        User user = userService.findUserProfileByJwt(jwt);
        Contact delete = contactService.deleteContact(contact,user);
        logger.info("Contact with ID {} deleted for user ID: {}", contactId, user.getId());
        MessageResponse messageResponse = new MessageResponse("Contact Deleted successfully");
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createContact(@RequestBody Contact contact
            ,@RequestHeader("Authorization") String jwt) throws Exception {
        logger.info("POST /api/contact - Creating new contact: {}", contact);
        User user = userService.findUserProfileByJwt(jwt);
        Contact contact1 = contactService.createContact(contact,user);
        MessageResponse messageResponse = new MessageResponse("Contact Created");
        logger.info("New contact created for user ID: {}", user.getId());
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }

    @GetMapping("/search")
    private ResponseEntity<List<Contact>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Contact> contacts = contactService.searchContacts(keyword,user);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}
