package com.example._Pearls.Controller;

import com.example._Pearls.Controller.UserController;
import com.example._Pearls.request.ChangeRequest;
import com.example._Pearls.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ChangeRequest changeRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up mock ChangeRequest
        changeRequest = new ChangeRequest();
        changeRequest.setOldPassword("oldPassword123");
        changeRequest.setNewPassword("newPassword123");
    }

    @Test
    void testChangePassword_Success() {
        // Call the controller method
        ResponseEntity<?> response = userController.changePassword(changeRequest);

        // Verify the result
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Password Changed Successfully", response.getBody());

        // Verify that the userService.changePassword() method was called once
        verify(userService, times(1)).changePassword(changeRequest);
    }

    @Test
    void testChangePassword_InvalidRequest() {
        // Set up an invalid ChangeRequest (empty old password)
        changeRequest.setOldPassword("");

        // Call the controller method
        ResponseEntity<?> response = userController.changePassword(changeRequest);

        // Verify that the response has the correct status and message for invalid input
        assertEquals(400, response.getStatusCodeValue());  // Assuming 400 for bad request in case of invalid input
        assertEquals("Invalid request", response.getBody());

        // userService.changePassword() should not be called in case of invalid input
        verify(userService, times(0)).changePassword(changeRequest);
    }


    @Test
    void testChangePassword_Failure() {
        // Simulate service throwing an exception (e.g., wrong old password)
        doThrow(new RuntimeException("Old password is incorrect")).when(userService).changePassword(changeRequest);

        // Call the controller method and catch the exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userController.changePassword(changeRequest);
        });

        // Verify the exception message
        assertEquals("Old password is incorrect", exception.getMessage());

        // Verify that userService.changePassword() was called once
        verify(userService, times(1)).changePassword(changeRequest);
    }

}
