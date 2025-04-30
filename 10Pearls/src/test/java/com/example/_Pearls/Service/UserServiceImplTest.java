package com.example._Pearls.Service;
import com.example._Pearls.Entity.Contact;
import com.example._Pearls.Entity.User;
import com.example._Pearls.Exception.IncorrectPasswordException;
import com.example._Pearls.Exception.UserNotRegisteredException;
import com.example._Pearls.config.JwtProvider;
import com.example._Pearls.repository.ContactRepository;
import com.example._Pearls.repository.UserRepository;
import com.example._Pearls.request.ChangeRequest;
import com.example._Pearls.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserProfileByJwt() throws Exception {
        String jwt = "valid.jwt.token";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        Mockito.mockStatic(JwtProvider.class);
        when(JwtProvider.getEmailFromToken(jwt)).thenReturn(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.findUserProfileByJwt(jwt);

        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindUserByEmail_UserNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UserNotRegisteredException.class, () -> userService.findUserByEmail(email));
    }

    @Test
    void testFindUserByEmail_Success() throws UserNotRegisteredException {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.findUserByEmail(email);

        assertEquals(email, result.getEmail());
    }

    @Test
    void testFindUserById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotRegisteredException.class, () -> userService.findUserById(userId));
    }

    @Test
    void testFindUserById_Success() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findUserById(userId);

        assertEquals(userId, result.getId());
    }

    @Test
    void testChangePassword_IncorrectOldPassword() {
        ChangeRequest request = new ChangeRequest("incorrectOldPassword", "newPassword");
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedOldPassword");

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(false);

        assertThrows(IncorrectPasswordException.class, () -> userService.changePassword(request));
    }

    @Test
    void testChangePassword_Success() {
        ChangeRequest request = new ChangeRequest("correctOldPassword", "newPassword");
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedOldPassword");

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(request.getNewPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(user)).thenReturn(user);

        userService.changePassword(request);

        verify(userRepository, times(1)).save(user);
        assertEquals("encodedNewPassword", user.getPassword());
    }
}
