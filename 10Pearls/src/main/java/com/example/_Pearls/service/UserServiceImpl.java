package com.example._Pearls.service;

import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import com.example._Pearls.Exception.IncorrectPasswordException;
import com.example._Pearls.Exception.UserNotRegisteredException;
import com.example._Pearls.config.JwtProvider;
import com.example._Pearls.repository.ContactRepository;
import com.example._Pearls.repository.UserRepository;
import com.example._Pearls.request.ChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email=JwtProvider.getEmailFromToken(jwt);

        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByEmail(String Email) throws UserNotRegisteredException {
        User user = userRepository.findByEmail(Email);
        if (user == null) {
            throw new UserNotRegisteredException("User not found with email "+Email);
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user=userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotRegisteredException("User not found with id "+userId);
        }
        return user.get();
    }

    @Override
    public List<Contact> findAllContactsByUserId(Long userId) {
        return contactRepository.findAllByUserId(userId);

    }

    @Override
    public void changePassword(ChangeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username);

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
