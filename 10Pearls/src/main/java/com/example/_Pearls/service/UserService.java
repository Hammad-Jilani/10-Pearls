package com.example._Pearls.service;


import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import com.example._Pearls.request.ChangeRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws Exception;

    User findUserByEmail(String Email) throws Exception;

    User findUserById(Long userId) throws Exception;

    List<Contact> findAllContactsByUserId(Long userId);

    void changePassword(ChangeRequest changeRequest);
}
