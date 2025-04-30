package com.example._Pearls.Controller;

import com.example._Pearls.Entity.User;
import com.example._Pearls.Exception.DuplicateEmailException;
import com.example._Pearls.config.JwtProvider;
import com.example._Pearls.repository.UserRepository;
import com.example._Pearls.request.LoginRequest;
import com.example._Pearls.response.AuthResponse;
import com.example._Pearls.response.MessageResponse;
import com.example._Pearls.service.CustomDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomDetailsService customDetailsService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody User user){
        User user1 = userRepository.findByEmail(user.getEmail());
        log.info("POST /auth/signup - Registering new User");
        if (user1!=null){
            log.error("POST /auth/signup - Email must be unique");
            throw new DuplicateEmailException("Email Already used. Enter another email");
        }
        User createUser =new User();
        createUser.setUsername(user.getUsername());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setEmail(user.getEmail());

        User saved = userRepository.save(createUser);

        Authentication authentication =new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("SignUp success");
        log.info("POST /auth/signup - User registered with ID {} and username {}",saved.getId(),saved.getUsername());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        log.info("Sign-in attempt for email: {}", username);

        if (password.isEmpty()){
            log.warn("Sign-in failed: Password is empty for email: {}", username);
            MessageResponse ms = new MessageResponse();
            ms.setMessage("Invalid request: Passwords cannot be empty");
            return new ResponseEntity<>(ms, HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Sign in success");

        log.info("Sign-in successful for email: {}", username);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        log.debug("Authenticating user: {}", username);
        UserDetails userDetails = customDetailsService.loadUserByUsername(username);
        if (userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        log.debug("Authentication successful for user: {}", username);
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
