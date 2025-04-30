package com.example._Pearls.Controller;

import com.example._Pearls.Entity.User;
import com.example._Pearls.request.ChangeRequest;
import com.example._Pearls.response.MessageResponse;
import com.example._Pearls.service.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangeRequest changeRequest){
        log.info("Received password change request");

        MessageResponse ms = new MessageResponse();

        if (changeRequest.getOldPassword().isEmpty() || changeRequest.getNewPassword().isEmpty()) {
            log.warn("Invalid password change request: One or both passwords are empty");
            ms.setMessage("Invalid request: Passwords cannot be empty");
            return new ResponseEntity<>(ms, HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(changeRequest);
        log.info("Password changed successfully ");
        ms.setMessage("Password Changed Successfully");
        return new ResponseEntity<>(ms, HttpStatus.OK);
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
