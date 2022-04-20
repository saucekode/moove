package com.shoplyst.user.web.controllers;

import com.shoplyst.user.data.model.Role;
import com.shoplyst.user.data.model.User;
import com.shoplyst.user.data.model.UserRoles;
import com.shoplyst.user.data.payload.request.LoginRequest;
import com.shoplyst.user.data.payload.request.SignupRequest;
import com.shoplyst.user.data.payload.response.ApiResponse;
import com.shoplyst.user.data.payload.response.JwtResponse;
import com.shoplyst.user.data.repository.RoleRepository;
import com.shoplyst.user.data.repository.UserRepository;
import com.shoplyst.user.security.jwt.JwtUtils;
import com.shoplyst.user.service.UserDetailsImpl;
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
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
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
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findRoleByUserRoles(UserRoles.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findRoleByUserRoles(UserRoles.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "storeOwner":
                        Role storeOwner = roleRepository.findRoleByUserRoles(UserRoles.STORE_OWNER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(storeOwner);
                        break;
                    default:
                        Role userRole = roleRepository.findRoleByUserRoles(UserRoles.CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true,"User registered successfully!"));
    }
}
