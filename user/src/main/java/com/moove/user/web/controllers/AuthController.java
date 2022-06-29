package com.moove.user.web.controllers;

import com.moove.user.data.model.UserRoles;
import com.moove.user.data.payload.request.LoginRequest;
import com.moove.user.data.payload.request.SignupRequest;
import com.moove.user.data.payload.response.ApiResponse;
import com.moove.user.data.payload.response.JwtResponse;
import com.moove.user.data.repository.RoleRepository;
import com.moove.user.data.repository.UserRepository;
import com.moove.user.data.model.Role;
import com.moove.user.data.model.User;
import com.moove.user.security.jwt.JwtUtils;
import com.moove.user.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getUsername(),
                roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, String siteURL) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Error: Email is already in use!"));
        }
        // Create new user's account
//        User user = new User(signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

//        logger.info("roles added: {}", strRoles);

        User user = new User(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );


        if (strRoles == null) {
            Role userRole = roleRepository.findByName(UserRoles.USER);
            roles.add(userRole);

        } else {
            strRoles.forEach(role -> {
//                logger.info("current role: {}", role);
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(UserRoles.ADMIN);


//                        logger.info("Customer admin role before add -> {}", adminRole);

                        roles.add(adminRole);

//                        logger.info("Customer admin role after add -> {}", roles);
                        break;
                    case "moderator":
                        Role moderator = roleRepository.findByName(UserRoles.MODERATOR);

//                        logger.info("Customer role name -> {}", storeOwner);

                        roles.add(moderator);

//                        logger.info("Customer role name -> {}", roles);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(UserRoles.USER);

//                        logger.info("Customer role name -> {}", UserRoles.CUSTOMER);

                        roles.add(userRole);

//                        logger.info("Customer role name -> {}", roles);

                }
            });
        }

//        logger.info("User roles -> {}", roles);

        user.setRoles(roles);

        userRepository.save(user);

//        logger.info("User to be saved -> {}", user);

        return ResponseEntity.ok(new ApiResponse(true,"User registered successfully!"));
    }

}
